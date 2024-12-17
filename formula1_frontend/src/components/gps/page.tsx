import GpResponse from "@/dto/gpResponse"
import { columns } from "./columns"
import { DataTable } from "./data-table"
import GpApi from "@/lib/gp_service";
import { AxiosResponse } from "axios";

async function getData(): Promise<AxiosResponse<GpResponse[]>> {
  // Fetch data from your API here.
  return GpApi.get();
}

export default async function DemoPage() {
  const data = (await getData()).data;

  return (
    <div className="container mx-auto py-10">
      <DataTable columns={columns} data={data} />
    </div>
  )
}
