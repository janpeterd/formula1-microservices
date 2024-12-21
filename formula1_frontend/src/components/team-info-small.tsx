import TeamResponse from "@/dto/teamResponse";
import TeamApi from "@/lib/team_service";
import { useState, useEffect } from "react";
import { API_URL } from "@/lib/utils";
import { Skeleton } from "./ui/skeleton";


function TeamInfoSmall({ teamCode }: { teamCode: string }) {
  const [team, setTeam] = useState<TeamResponse>();
  const [imageUrlHasError, setImageUrlHasError] = useState<boolean>(false);
  const [logoUrlHasError, setLogoUrlHasError] = useState<boolean>(false);

  useEffect(() => {
    const fetchTeam = async () => {
      try {
        const teamResponse = await TeamApi.getByCode(teamCode);
        setTeam(teamResponse.data);
      } catch (err) {
        console.error("Failed to fetch data: ", err);
      }
    };
    fetchTeam();
  }, [teamCode]);

  return (
    <div>
      {team !== undefined
        ?
        <div className="border-neutral-300 border-r-2 border-b-2 rounded-br-2xl">

          <div>
            <div className="flex gap-x-6 items-center px-2">
              {logoUrlHasError
                ?
                <Skeleton className="w-40 h-[115px] bg-line-pattern" />
                :
                <img src={`${API_URL}/${team.logoUrl}`} alt="logo" className="w-40 h-[115px] object-contain" onError={() => setLogoUrlHasError(true)} />
              }
              <p className="text-2xl font-bold text-center w-full py-6 bg-bg_accent rounded-lg">{team.name}</p>
            </div>
          </div>
          <div className="flex justify-center items-center">
            {imageUrlHasError
              ?
              <Skeleton className="w-full h-[115px] bg-line-pattern" />
              :
              <img src={`${API_URL}/${team.imageUrl}`} alt="imageUrl" className="w-full h-[115px] object-contain" onError={() => setImageUrlHasError(true)} />
            }
          </div>
        </div>
        :
        <p>No team found</p>
      }
    </div>
  )
}

export default TeamInfoSmall;
