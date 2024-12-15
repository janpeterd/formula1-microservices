
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
import axios from "axios"
import ObjectSelect from "./object-select"
import { useEffect, useState } from "react"
import DriverApi from "@/lib/driver_service"
import DriverResponse from "@/dto/driverResponse"
import TeamResponse from "@/dto/teamResponse"
import TeamApi from "@/lib/team_service"

const gpSchema = z.object({
  name: z.string().min(2).max(50),
  country: z.string().min(2).max(50),
  city: z.string().min(2).max(50),
  distanceMeters: z.string().transform((val) => Number(val)).pipe(z.number().positive()),
  laps: z.string().transform((val) => Number(val)).pipe(z.number().positive()),
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
        const validTypes = ['image/jpeg', 'image/png', 'image/webp'];
        return validTypes.includes(file.type);
      },
      "Please upload a JPG, PNG, or WEBP image"
    ),
  trackImageUrl: z
    .instanceof(File)
    .refine(
      (file) => file && file.size <= 35 * 1024 * 1024,
      "File size should be 35MB or less"
    )
    .refine(
      (file) => {
        if (!file) return false;
        const validTypes = ['image/jpeg', 'image/png', 'image/webp'];
        return validTypes.includes(file.type);
      },
      "Please upload a JPG, PNG, or WEBP image"
    )
})


type ValueLabel = {
  value: string;
  label: string;
};


function GrandPrixForm() {

  const [drivers, setDrivers] = useState<ValueLabel[]>([]);
  const [teams, setTeams] = useState<ValueLabel[]>([]);

  useEffect(() => {
    const fetchDrivers = async () => {
      try {
        const response = await DriverApi.get();
        //setDrivers(response.data);
        const mappedDrivers: ValueLabel[] = response.data.map((driver: DriverResponse) => ({
          value: driver.driverCode, // or the unique identifier
          label: `${driver.firstName} ${driver.lastName}`, // Customize as needed
        }));
        setDrivers(mappedDrivers);
      } catch (err) {
        console.error("Failed to fetch drivers from API: ", err);
      }
    };

    const fetchTeams = async () => {
      try {
        const response = await TeamApi.get();
        //setTeams(response.data);
        const mappedTeams: ValueLabel[] = response.data.map((team: TeamResponse) => ({
          value: team.teamCode, // or the unique identifier
          label: `${team.name}`, // Customize as needed
        }));
        setTeams(mappedTeams);
      } catch (err) {
        console.error("Failed to fetch teams from API: ", err);
      }
    };
    fetchDrivers();
    fetchTeams();
  }, []);


  // 1. Define your form.
  const form = useForm<z.infer<typeof gpSchema>>({
    resolver: zodResolver(gpSchema),
    defaultValues: {
      name: "",
    },
  })

  type Paths = {
    imageUrl: string;
    trackImageUrl: string
  }

  // 2. Define a submit handler.
  async function onSubmit(values: z.infer<typeof gpSchema>) {
    // ✅ This will be type-safe and validated.
    console.log("POSTING: ", values);

    const paths: Paths = { imageUrl: "", trackImageUrl: "" };

    try {
      const headers = { "Content-Type": "multipart/form-data" };

      // Upload imageUrl
      if (values.imageUrl) {
        const fileResponse = await axios.post(
          "http://localhost:8084/api/upload",
          { file: values.imageUrl },
          { headers }
        );
        paths.imageUrl = fileResponse.data;
      }

      // Upload trackImageUrl
      if (values.trackImageUrl) {
        const trackResponse = await axios.post(
          "http://localhost:8084/api/upload",
          { file: values.trackImageUrl },
          { headers }
        );
        paths.trackImageUrl = trackResponse.data;
      }

      // Submit final GP data
      const finalResponse = await axios.post("http://localhost:8082/api/gp", {
        ...values,
        imageUrl: paths.imageUrl,
        trackImageUrl: paths.trackImageUrl,
      });

      console.log("Final response:", finalResponse.data);
    } catch (error) {
      console.error("Error occurred:", error);
    }
  }

  return (
    <>
      <div className="container mx-auto lg:w-2/3 bg-bg_accent p-8 rounded">
        <h1 className="text-4xl my-8 font-f1 font-bold text-center">Add a Grand Prix</h1>
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
                        value={field.value ? field.value.toISOString().split("T")[0] : ""}
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
              render={({ field }) => (
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
              )}
            />
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
                      <Input
                        id="fileUpload"
                        name="imageUrl"
                        type="file"
                        onChange={(e) => {
                          if (e.target.files && e.target.files[0]) {
                            field.onChange(e.target.files[0]); // Set the file in the form state
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
                      <Input
                        id="trackImageUpload"
                        name="trackImageUrl"
                        type="file"
                        onChange={(e) => {
                          if (e.target.files && e.target.files[0]) {
                            field.onChange(e.target.files[0]); // Set the file in the form state
                          }
                        }}
                      />
                    </>
                  </FormControl>
                  <FormMessage />
                </FormItem>
              )}
            />
            <Button type="submit" className="bg-accent font-f1 font-bold text-lg">Submit</Button>
          </form>
        </Form>
      </div>
    </>
  )
}


export default GrandPrixForm
