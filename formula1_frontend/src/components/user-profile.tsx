import { Link } from "react-router-dom";
import { Avatar, AvatarFallback, AvatarImage } from "./ui/avatar";
import { DropdownMenu, DropdownMenuContent, DropdownMenuItem, DropdownMenuLabel, DropdownMenuSeparator, DropdownMenuTrigger } from "./ui/dropdown-menu";

type UserProfileProps = {
  image: string;
  onLogOut: () => void;
};

function UserProfile({ image, onLogOut }: UserProfileProps) {
  return (
    <>
      <DropdownMenu>
        <DropdownMenuTrigger>
          <Avatar>
            <AvatarImage src={image} />
            <AvatarFallback>IMG</AvatarFallback>
          </Avatar>
        </DropdownMenuTrigger>
        <DropdownMenuContent>
          <DropdownMenuLabel>My Account</DropdownMenuLabel>
          <DropdownMenuSeparator />
          <DropdownMenuItem onClick={onLogOut}>Log Out</DropdownMenuItem>
          <DropdownMenuSeparator />
          <DropdownMenuItem>
            <Link to="/grand-prix-add">Add Grand Prix</Link>
          </DropdownMenuItem>
          <DropdownMenuItem>
            <Link to="/gp-admin">Manage Grand Prix</Link>
          </DropdownMenuItem>
        </DropdownMenuContent>
      </DropdownMenu>
      <div>
      </div>
    </>
  )

}
export default UserProfile;

