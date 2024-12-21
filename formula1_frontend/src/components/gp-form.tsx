
import { Button } from "@/components/ui/button"
import "../index.css"
import { z } from "zod"
import { useForm } from "react-hook-form"
import { zodResolver } from "@hookform/resolvers/zod"
import {
  Form,
  FormControl,
  FormField,
  FormItem,
  FormMessage,
} from "@/components/ui/form"
import { Input } from "@/components/ui/input"
import { Label } from "@/components/ui/label"
import ObjectSelect from "./object-select"
import { useEffect, useState } from "react"
import DriverApi from "@/lib/driver_service"
import DriverResponse from "@/dto/driverResponse"
import TeamResponse from "@/dto/teamResponse"
import TeamApi from "@/lib/team_service"
import GpApi from "@/lib/gp_service"
import GpRequest from "@/dto/gpRequest"
import ImageApi from "@/lib/image_service"
import { useNavigate, useParams } from "react-router-dom"
import { useToast } from "@/hooks/use-toast"
import { API_URL, fileObjectToDataUrl, urlToFile } from "@/lib/utils"

const validTypes = ['jpeg', 'png', 'webp', 'avif', 'jpg'];
const gpSchema = z.object({
  name: z.string().min(2).max(50),
  country: z.string().min(2).max(50),
  city: z.string().min(2).max(50),
  distanceMeters: z.union([z.string(), z.number()]) // Allow either a string or a number
    .transform((val) => typeof val === 'string' ? Number(val) : val) // Only transform if it's a string
    .pipe(z.number().positive()),
  laps: z.union([z.string(), z.number()]) // Allow either a string or a number
    .transform((val) => typeof val === 'string' ? Number(val) : val) // Only transform if it's a string
    .pipe(z.number().positive()),
  raceDate: z.date(),
  winningTeamCode: z.string().length(36),
  winningDriverCode: z.string().length(36),
  secondDriverCode: z.string().length(36),
  thirdDriverCode: z.string().length(36),
  imageUrl: z
    .instanceof(File)
    .refine(
      (file) => file && file.size <= 35 * 1024 * 1024,
      "File size should be 35MB or less"
    )
    .refine(
      (file) => {
        if (!file) return false;

        // Extract file extension (case-insensitive)
        const fileExtension = file.name.split('.').pop()?.toLowerCase();

        // Check if the file extension is in the valid types list
        return fileExtension && validTypes.includes(fileExtension);
      },
      "Please upload a JPG, PNG, AVIF or WEBP image"
    ),
  trackImageUrl: z
    .instanceof(File)
    .refine(
      (file) => file && file.size <= 5 * 1024 * 1024,
      "File size should be 5MB or less"
    )
    .refine(
      (file) => {
        if (!file) return false;

        // Extract file extension (case-insensitive)
        const fileExtension = file.name.split('.').pop()?.toLowerCase();

        // Check if the file extension is in the valid types list
        return fileExtension && validTypes.includes(fileExtension);
      },
      "Please upload a JPG, PNG, AVIF or WEBP image"
    ),
})

type Paths = {
  imageUrl: string;
  trackImageUrl: string
}

type ValueLabel = {
  value: string;
  label: string;
};

function GrandPrixForm() {
  const { gpCode } = useParams();
  const { toast } = useToast();
  const navigate = useNavigate();
  const [drivers, setDrivers] = useState<ValueLabel[]>([]);
  const [teams, setTeams] = useState<ValueLabel[]>([]);
  const [imageSrc, setImageSrc] = useState<string>("");
  const [trackImageSrc, setTrackImageSrc] = useState<string>("");
  const [hasUploadedImages, setHasUploadedImages] = useState(false);

  // Initialize form with empty default values
  const form = useForm<z.infer<typeof gpSchema>>({
    resolver: zodResolver(gpSchema),
    defaultValues: {
      name: "",
      country: "",
      city: "",
      distanceMeters: 0,
      laps: 0,
      winningDriverCode: "",
      secondDriverCode: "",
      thirdDriverCode: "",
      winningTeamCode: "",
      raceDate: new Date(),
    }
  });

  useEffect(() => {
    const fetchGpData = async () => {
      if (gpCode && !hasUploadedImages) {
        try {
          const response = await GpApi.getByCode(gpCode);
          setImageSrc(`${API_URL}/${response.data.imageUrl}`);
          setTrackImageSrc(`${API_URL}/${response.data.trackImageUrl}`);
          const imageData = await urlToFile(`${API_URL}/${response.data.imageUrl}`);
          const trackImageData = await urlToFile(`${API_URL}/${response.data.trackImageUrl}`);

          // Use form.reset to update all form values at once
          form.reset({
            ...response.data,
            imageUrl: imageData,
            trackImageUrl: trackImageData,
            winningDriverCode: response.data.winningDriver.driverCode,
            secondDriverCode: response.data.secondDriver.driverCode,
            thirdDriverCode: response.data.thirdDriver.driverCode,
            winningTeamCode: response.data.winningTeam.teamCode,
            raceDate: new Date(response.data.raceDate)
          });
        } catch (error) {
          console.error("Failed to fetch GP data:", error);
          toast({
            variant: "destructive",
            title: "Error loading Grand Prix data",
            description: "Failed to load the Grand Prix details."
          });
        }
      }
    };

    const fetchOptions = async () => {
      try {
        const [driverResponse, teamResponse] = await Promise.all([
          DriverApi.get(),
          TeamApi.get(),
        ]);

        setDrivers(
          driverResponse.data.map((driver: DriverResponse) => ({
            value: driver.driverCode,
            label: `${driver.firstName} ${driver.lastName}`,
          }))
        );

        setTeams(
          teamResponse.data.map((team: TeamResponse) => ({
            value: team.teamCode,
            label: team.name,
          }))
        );
      } catch (err) {
        console.error("Failed to fetch data: ", err);
        toast({
          variant: "destructive",
          title: "Error loading data",
          description: "Failed to load drivers and teams data."
        });
      }
    };

    fetchGpData();
    fetchOptions();
  }, [gpCode, hasUploadedImages, form, toast]);


  const handleImageUpload = (file: File, isTrack: boolean) => {
    setHasUploadedImages(true); // Mark images as uploaded
    fileObjectToDataUrl(file).then((result) => {
      if (isTrack) {
        setTrackImageSrc(result);
      } else {
        setImageSrc(result);
      }
    })
  };

  // 2. Define a submit handler.
  async function onSubmit(values: z.infer<typeof gpSchema>) {
    const paths: Paths = { imageUrl: "", trackImageUrl: "" };
    // Upload imageUrl
    if (values.imageUrl && values.trackImageUrl) {
      try {

        const imageReponse = await ImageApi.post({ file: values.imageUrl });
        toast({
          title: "Uploaded image",
          description: (
            <div className="flex gap-x-2 justify-center items-center">
              <img src={`${API_URL}/${imageReponse.data}`} alt="uploaded image" className="w-20 h-20 object-cover rounded-lg" />
              <p className="leading-7 [&:not(:first-child)]:mt-6">Succesfully uploaded Grand Prix Image</p>
            </div >
          ),
        })
        paths.imageUrl = imageReponse.data;
      } catch (error: any) {
        toast({
          variant: "destructive",
          title: "Error uploading image",
          description: `Something went wrong when trying to upload the image. ${error.message}`,
        })

      }

      try {
        const trackResponse = await ImageApi.post({ file: values.trackImageUrl });
        toast({
          title: "Uploaded track image",
          description: (
            <div className="flex gap-x-2 justify-center items-center">
              <img src={`${API_URL}/${trackResponse.data}`} alt="uploaded image" className="w-20 h-20 object-cover rounded-lg" />
              <p className="leading-7 [&:not(:first-child)]:mt-6">Succesfully uploaded Grand Prix Track Image</p>
            </div >
          ),
        });
        paths.trackImageUrl = trackResponse.data;
      } catch (error: any) {
        toast({
          variant: "destructive",
          title: "Error uploading track image",
          description: `Something went wrong when trying to upload the track image. ${error.message}`,
        })
      }

      const payload: GpRequest = {
        ...values,
        imageUrl: paths.imageUrl, // Use existing if not uploaded
        trackImageUrl: paths.trackImageUrl,
      };
      console.log("payload", payload)

      if (gpCode) {
        // Update existing GP
        try {
          await GpApi.put(gpCode, payload);
          toast({
            title: "Grand Prix updated",
            description: `Succesfully updated Grand Prix ${payload.name} - ${payload.country}.`,
          })
          console.log("GP updated successfully.");
        } catch (error: any) {
          toast({
            variant: "destructive",
            title: "Error updating Grand Prix",
            description: `Something went wrong when trying to update the Grand Prix. ${error.message}`,
          })
        }
      } else {
        // Create new GP
        try {
          await GpApi.post(payload);
          console.log("GP created successfully.");
          toast({
            title: "Grand Prix created",
            description: `Succesfully created Grand Prix ${payload.name} - ${payload.country}.`,
          })
        } catch (error) {
          console.error("Error occurred:", error);
          toast({
            variant: "destructive",
            title: "Error creating new Grand Prix",
            description: `Something went wrong when trying to create Grand Prix.`,
          })
        }
      }
      navigate("/gp-admin");
    }
  }

  return (
    <>
      <div className="container mx-auto lg:w-2/3 bg-bg_accent p-8 rounded">
        <h1 className="text-4xl my-8 font-f1 font-bold text-center">
          {gpCode ? "Edit Grand Prix" : "Add a Grand Prix"}
        </h1>
        <Form {...form}>
          <form onSubmit={form.handleSubmit(onSubmit)} className="space-y-8">
            <FormField
              control={form.control}
              name="name"
              render={({ field }) => (
                <FormItem>
                  <FormControl>
                    <>
                      <Label className="block text-left" htmlFor="gpName">Name</Label>
                      <Input placeholder="Name of the Grand Prix" id="gpName" {...field} />
                    </>
                  </FormControl>
                  <FormMessage />
                </FormItem>
              )}
            />
            <FormField
              control={form.control}
              name="country"
              render={({ field }) => (
                <FormItem>
                  <FormControl>
                    <>
                      <Label className="block text-left" htmlFor="country">Country</Label>
                      <Input placeholder="Country" id="country" {...field} />
                    </>
                  </FormControl>
                  <FormMessage />
                </FormItem>
              )}
            />
            <FormField
              control={form.control}
              name="city"
              render={({ field }) => (
                <FormItem>
                  <FormControl>
                    <>
                      <Label className="block text-left" htmlFor="city">City</Label>
                      <Input placeholder="City" id="city" {...field} />
                    </>
                  </FormControl>
                  <FormMessage />
                </FormItem>
              )}
            />
            <FormField
              control={form.control}
              name="distanceMeters"
              render={({ field }) => (
                <FormItem>
                  <FormControl>
                    <>
                      <Label className="block text-left" htmlFor="distance">Distance</Label>
                      <Input placeholder="Distance in meters" type="number" id="distance" {...field} />
                    </>
                  </FormControl>
                  <FormMessage />
                </FormItem>
              )}
            />
            <FormField
              control={form.control}
              name="laps"
              render={({ field }) => (
                <FormItem>
                  <FormControl>
                    <>
                      <Label className="block text-left" htmlFor="laps">Laps</Label>
                      <Input placeholder="Laps in GP" type="number" id="laps" {...field} />
                    </>
                  </FormControl>
                  <FormMessage />
                </FormItem>
              )}
            />
            <FormField
              control={form.control}
              name="raceDate"
              render={({ field }) => (
                <FormItem>
                  <FormControl>
                    <>
                      <Label className="block text-left" htmlFor="dateInput">Date of race</Label>
                      <Input
                        type="date"
                        id="dateInput"
                        {...field}
                        value={field.value ? new Date(field.value).toISOString().split("T")[0] : ""}
                        onChange={(e) => field.onChange(new Date(e.target.value))}
                      />
                    </>
                  </FormControl>
                  <FormMessage />
                </FormItem>
              )}
            />
            <FormField
              control={form.control}
              name="winningTeamCode"
              render={({ field }) => {
                return (
                  <FormItem>
                    <FormControl>
                      <>
                        <Label className="block text-left" htmlFor="winningTeam">Winning team</Label>
                        <ObjectSelect
                          objectName="team"
                          objects={teams}
                          value={field.value}
                          onChange={field.onChange} />
                      </>
                    </FormControl>
                    <FormMessage />
                  </FormItem>
                )
              }} />
            <FormField
              control={form.control}
              name="winningDriverCode"
              render={({ field }) => (
                <FormItem>
                  <FormControl>
                    <>
                      <Label className="block text-left" htmlFor="firstDriver">Winner</Label>
                      <ObjectSelect
                        objectName="driver"
                        objects={drivers}
                        value={field.value}
                        onChange={field.onChange} // Pass the onChange handler
                      />

                    </>
                  </FormControl>
                  <FormMessage />
                </FormItem>
              )}
            />
            <FormField
              control={form.control}
              name="secondDriverCode"
              render={({ field }) => (
                <FormItem>
                  <FormControl>
                    <>
                      <Label className="block text-left" htmlFor="secondDriver">Second place driver</Label>
                      <ObjectSelect
                        objectName="driver"
                        objects={drivers}
                        value={field.value}
                        onChange={field.onChange} // Pass the onChange handler
                      />
                    </>
                  </FormControl>
                  <FormMessage />
                </FormItem>
              )}
            />
            <FormField
              control={form.control}
              name="thirdDriverCode"
              render={({ field }) => (
                <FormItem>
                  <FormControl>
                    <>
                      <Label className="block text-left" htmlFor="thirdDriver">Third place driver</Label>
                      <ObjectSelect
                        objectName="driver"
                        objects={drivers}
                        value={field.value}
                        onChange={field.onChange} // Pass the onChange handler
                      />
                    </>
                  </FormControl>
                  <FormMessage />
                </FormItem>
              )}
            />
            <FormField control={form.control}
              name="imageUrl"
              render={({ field }) => (
                <FormItem>
                  <FormControl>
                    <>
                      <Label className="block text-left" htmlFor="imageUpload">Image</Label>
                      {imageSrc
                        ?
                        <img src={imageSrc} alt="Grand Prix Image" className="rounded-lg max-w-36 max-h-36 ml-1" />
                        : null
                      }
                      <Input
                        id="fileUpload"
                        name="imageUrl"
                        type="file"
                        onChange={(e) => {
                          if (e.target.files && e.target.files[0]) {
                            field.onChange(e.target.files[0]); // Set the file in the form state
                            handleImageUpload(e.target.files[0], false);
                          }
                        }}
                      />
                    </>
                  </FormControl>
                  <FormMessage />
                </FormItem>
              )}
            />
            <FormField control={form.control}
              name="trackImageUrl"
              render={({ field }) => (
                <FormItem>
                  <FormControl>
                    <>
                      <Label className="block text-left" htmlFor="trackImageUpload">Track Image</Label>
                      {trackImageSrc
                        ?
                        <img src={trackImageSrc} alt="Grand Prix Track Image" className="rounded-lg max-w-36 max-h-36 ml-1" />
                        : null
                      }
                      <Input
                        id="trackImageUpload"
                        name="trackImageUrl"
                        type="file"
                        onChange={(e) => {
                          if (e.target.files && e.target.files[0]) {
                            field.onChange(e.target.files[0]);
                            handleImageUpload(e.target.files[0], true);
                          }
                        }}
                      />
                    </>
                  </FormControl>
                  <FormMessage />
                </FormItem>
              )}
            />
            <Button type="submit" className="bg-accent font-f1 font-bold text-lg">
              {gpCode ? "Update" : "Submit"}
            </Button>
          </form>
        </Form>
      </div >
    </>
  )
}


export default GrandPrixForm;
