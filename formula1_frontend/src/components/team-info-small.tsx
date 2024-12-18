import TeamResponse from "@/dto/teamResponse";
import TeamApi from "@/lib/team_service";
import { useState, useEffect } from "react";
import GrandPrixStat from "./gp-stat";


function TeamInfoSmall({ teamCode }: { teamCode: string }) {
  const [team, setTeam] = useState<TeamResponse>();

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
      {team != null
        ?
        <div className="flex gap-x-6">
          <img src="https://media.formula1.com/image/upload/f_auto,c_limit,q_75,w_1320/content/dam/fom-website/2018-redesign-assets/team%20logos/mercedes" alt="logo" className="w-40 object-contain" />
          <GrandPrixStat label="Team" stat={team.name} />
        </div>
        :
        <p>No team found</p>
      }
    </div>
  )
}

export default TeamInfoSmall;
