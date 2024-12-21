import { isSignedInState } from "@/store";
import { useRecoilValue } from "recoil";
import { ReactNode } from "react";
import LoginNeededPage from "./login-needed-pages";


function Secure({ children }: { children: ReactNode }) {
  const isSignedIn = useRecoilValue(isSignedInState);

  if (!isSignedIn) {
    // If not signed in, return a prompt or redirect to sign-in
    return <LoginNeededPage />;
  }

  // If signed in, render the children (which will be ManageGps or any other components passed)
  return <div>{children}</div>;
}

export default Secure;
