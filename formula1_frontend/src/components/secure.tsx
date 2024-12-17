import { isSignedInState } from "@/store";
import { useRecoilValue } from "recoil";
import GoogleSignInButton from "./google-sign-in";


function Secure({ children }) {
  const isSignedIn = useRecoilValue(isSignedInState);

  if (!isSignedIn) {
    // If not signed in, return a prompt or redirect to sign-in
    return <GoogleSignInButton renderButtonElId="pageSignInButton" />;
  }

  // If signed in, render the children (which will be ManageGps or any other components passed)
  return <div>{children}</div>;
}

export default Secure;
