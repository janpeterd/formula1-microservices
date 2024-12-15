
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
import DriverSelect from "./driver-select"

const gpSchema = z.object({
  name: z.string().min(2).max(50),
  country: z.string().min(2).max(50),
  city: z.string().min(2).max(50),
  distanceMeters: z.string().transform((val) => Number(val)).pipe(z.number().positive()),
  laps: z.string().transform((val) => Number(val)).pipe(z.number().positive()),
  winningTeamCode: z.string().length(36).optional(),
  winningDriverCode: z.string().length(36).optional(),
  secondDriverCode: z.string().length(36).optional(),
  thirdDriverCode: z.string().length(36).optional(),
  fileUrl: z
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

function GrandPrixForm() {




  // 1. Define your form.
  const form = useForm<z.infer<typeof gpSchema>>({
    resolver: zodResolver(gpSchema),
    defaultValues: {
      name: "",
    },
  })

  // 2. Define a submit handler.
  function onSubmit(values: z.infer<typeof gpSchema>) {
    // Do something with the form values.
    // ✅ This will be type-safe and validated.
    console.log("POSTING: ", values)

    if (values.fileUrl != null) {
      axios.post('http://localhost:8084/api/upload', {
        "file": values.fileUrl
      }, {
        headers: {
          "Content-Type": "multipart/form-data", // Set multipart header
        }

      }).then(function (response) {
        console.log(response);
        axios.post('http://localhost:8082/api/gp', {
          ...values, fileUrl: response.data
        }).then(function (response) {
          console.log(response);
        }).catch(function (error) {
          console.log(error);
        });
      }).catch(function (error) {
        console.log(error);
      });
    }


  }

  return (
    <>
      <DriverSelect />
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
                      <Label className="block text-left" htmlFor="gpName">Driver</Label>
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
                      <Label className="block text-left" htmlFor="country">Driver</Label>
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
                      <Label className="block text-left" htmlFor="city">Driver</Label>
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
                      <Label className="block text-left" htmlFor="distance">Driver</Label>
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
                      <Label className="block text-left" htmlFor="laps">Driver</Label>
                      <Input placeholder="Laps in GP" type="number" id="laps" {...field} />
                    </>
                  </FormControl>
                  <FormMessage />
                </FormItem>
              )}
            />
            {/* TODO: dropdown  */}
            <FormField
              control={form.control}
              name="winningTeamCode"
              render={({ field }) => (
                <FormItem>
                  <FormControl>
                    <>
                      <Label className="block text-left" htmlFor="winningTeam">Driver</Label>
                      <Input placeholder="Winning team code" id="winningTeam" {...field} />
                    </>
                  </FormControl>
                  <FormMessage />
                </FormItem>
              )}
            />
            {/* TODO: dropdown  */}
            <FormField
              control={form.control}
              name="winningDriverCode"
              render={({ field }) => (
                <FormItem>
                  <FormControl>
                    <>
                      <Label className="block text-left" htmlFor="firstDriver">Driver</Label>
                      <Input placeholder="Winning driver code" id="firstDriver" {...field} />
                    </>
                  </FormControl>
                  <FormMessage />
                </FormItem>
              )}
            />
            {/* TODO: dropdown  */}
            <FormField
              control={form.control}
              name="secondDriverCode"
              render={({ field }) => (
                <FormItem>
                  <FormControl>
                    <>
                      <Label className="block text-left" htmlFor="secondDriver">Driver</Label>
                      <Input placeholder="Second place driver code" id="secondDriver" {...field} />
                    </>
                  </FormControl>
                  <FormMessage />
                </FormItem>
              )}
            />
            {/* TODO: dropdown  */}
            <FormField
              control={form.control}
              name="thirdDriverCode"
              render={({ field }) => (
                <FormItem>
                  <FormControl>
                    <>
                      <Label className="block text-left" htmlFor="thirdDriver">Driver</Label>
                      <Input placeholder="Third place driver code" id="thirdDriver" {...field} />
                    </>
                  </FormControl>
                  <FormMessage />
                </FormItem>
              )}
            />
            <FormField control={form.control}
              name="fileUrl"
              render={({ field }) => (
                <FormItem>
                  <FormControl>
                    <>
                      <Label className="block text-left" htmlFor="imageUpload">Image</Label>
                      <Input
                        id="fileUpload"
                        name="fileUrl"
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
