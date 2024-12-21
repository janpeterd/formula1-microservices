import GpResponse from "@/dto/gpResponse";
import { useEffect, useState } from "react";
import { columns } from "./gps/columns"
import GpApi from "@/lib/gp_service";
import { DataTable } from "./gps/data-table";
import { useToast } from "@/hooks/use-toast";
import { useNavigate } from "react-router-dom";


function GrandPrixAdminPage() {
  const [gps, setGps] = useState<GpResponse[]>([]);
  const { toast } = useToast();
  const navigate = useNavigate();

  const fetchGps = async () => {
    try {
      const response = await GpApi.get();
      setGps(response.data);

    } catch (err) {
      console.error("Failed to fetch gps from API: ", err);
    }
  };
  useEffect(() => {
    fetchGps();
  }, []);
  return (
    <div className="w-ful px-4">
      <DataTable data={gps} columns={columns} reload_data={fetchGps} toast={toast} navigate={navigate} />
    </div>
  )
}
export default GrandPrixAdminPage;
