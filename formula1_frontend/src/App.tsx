import { Button } from "@/components/ui/button"
import './App.css'
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
import { Label } from "./components/ui/label"
import axios from "axios"

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
  file: z
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

function ProfileForm() {
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
    axios.post('http://localhost:8082/api/gp', {
      values
    }).then(function (response) {
      console.log(response);
    }).catch(function (error) {
      console.log(error);
    });
  }

  return (
    <div className="container mx-auto">
      <h1 className="text-4xl font-serif my-8">Add a Grand Prix</h1>
      <Form {...form}>
        <form onSubmit={form.handleSubmit(onSubmit)} className="space-y-8">
          <FormField
            control={form.control}
            name="name"
            render={({ field }) => (
              <FormItem>
                <FormControl>
                  <Input placeholder="Name of the Grand Prix" {...field} />
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
                  <Input placeholder="Country" {...field} />
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
                  <Input placeholder="City" {...field} />
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
                  <Input placeholder="Distance in meters" type="number" valueAsNumber {...field} />
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
                  <Input placeholder="Laps in GP" type="number" {...field} />
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
                  <Input placeholder="Winning team code" {...field} />
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
                  <Input placeholder="Winning driver code" {...field} />
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
                  <Input placeholder="Second place driver code" {...field} />
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
                  <Input placeholder="Third place driver code" {...field} />
                </FormControl>
                <FormMessage />
              </FormItem>
            )}
          />
          <FormField control={form.control}
            name="file"
            render={({ field }) => (
              <FormItem>
                <FormControl>
                  <>
                    <Label className="block text-left" htmlFor="imageUpload">Image</Label>
                    <Input
                      id="fileUpload"
                      name="file"
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
          <Button type="submit">Submit</Button>
        </form>
      </Form>
    </div>
  )
}

function App() {
  return (
    <ProfileForm />
  )
}
export default App;
