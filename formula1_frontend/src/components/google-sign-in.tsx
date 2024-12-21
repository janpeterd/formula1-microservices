import { useCallback, useEffect, useState } from "react";
import { jwtDecode } from "jwt-decode";
import Cookies from "js-cookie";
import UserProfile from "./user-profile";
import { useRecoilState } from "recoil";
import { isSignedInState } from "@/store";
import { useToast } from "@/hooks/use-toast";

function GoogleSignInButton({ renderButtonElId }: { renderButtonElId: string }) {
  const [isSignedIn, setIsSignedIn] = useRecoilState(isSignedInState);
  const [decodedToken, setDecodedToken] = useState({});
  const { toast } = useToast();

  const renderLoginButton = useCallback(() => {
    console.log("RENDERING LOGIN BUTTON")
    /* Render the Sign-In button */
    // @ts-expect-error google doesnt exist according to typescript
    window.google.accounts.id.renderButton(
      document.getElementById(renderButtonElId),
      { size: "medium", shape: "pill", text: "signin" }
    );
  }, [renderButtonElId]);

  // Stable googleLogout function
  const googleLogout = useCallback(() => {
    Cookies.remove("idToken");
    setIsSignedIn(false);

    toast({
      title: "Succesfully logged out",
      description: `You are now logged out.`,
    })
    console.log("renderButtonElId", renderButtonElId)
    const element = document.getElementById(renderButtonElId);
    console.log("logout element", element)
    if (element !== null) {
      element.classList.remove("hidden");
      element.classList.add("block");
      console.log("removed hidden element");
    }

    renderLoginButton();
  }, [renderLoginButton, renderButtonElId, setIsSignedIn, toast]);



  // @ts-expect-error google callback response unknown type
  const handleCallbackResponse = useCallback((response) => {
    document.getElementById(renderButtonElId)?.classList.add("hidden");

    const idToken = response.credential;
    setIsSignedIn(true);
    console.log("Encoded JWT ID Token:", idToken);

    // Store the token in a secure cookie
    Cookies.set("idToken", idToken, { expires: 1, secure: true, sameSite: "Strict" });

    // Decode the token
    const decoded = jwtDecode(idToken);

    setDecodedToken(decoded);
    toast({
      title: "Succesfully logged in",
      // @ts-expect-error: name, email doesnt exist
      description: `You are now logged in as: ${decoded.name} (${decoded.email})`,
    })
  }, [renderButtonElId, setIsSignedIn, toast]);

  useEffect(() => {


    function googleInit() {
      console.log("GOOGLE OAUTH2 INIT");

      // @ts-expect-error google type error
      if (window.google && window.google.accounts) {
        // @ts-expect-error google type error
        window.google.accounts.id.initialize({
          client_id: "238164061415-sci0phqk4p0e2bj04dmcm430mu86lo5r.apps.googleusercontent.com",
          callback: handleCallbackResponse,
        });
      } else {
        console.error("Google API not loaded");
      }
    }

    const idToken = Cookies.get("idToken");
    if (idToken) {
      const decodedToken = jwtDecode(idToken);
      setDecodedToken(decodedToken);
      if (decodedToken?.exp) {
        const isExpired = Date.now() >= decodedToken.exp * 1000;
        if (isExpired) {
          googleLogout();
        } else {
          setIsSignedIn(true);
          console.log("Encoded JWT ID Token:", idToken);
        }
      }
    } else {
      googleInit();
    }
    renderLoginButton();
  }, [handleCallbackResponse, toast, setIsSignedIn, renderButtonElId, googleLogout, renderLoginButton]);



  return (
    <div>
      {isSignedIn ?
        // @ts-expect-error: picture doesnt exist
        <UserProfile image={decodedToken.picture} onLogOut={googleLogout} />
        : null
      }
      <div id={renderButtonElId}></div>
    </div>
  );
}

export default GoogleSignInButton;
