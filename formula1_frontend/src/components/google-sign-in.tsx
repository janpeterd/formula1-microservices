import { useEffect, useState } from "react";
import { jwtDecode } from "jwt-decode";
import Cookies from "js-cookie";
import UserProfile from "./user-profile";
import { useRecoilState } from "recoil";
import { isSignedInState } from "@/store";

function GoogleSignInButton({ renderButtonElId }: { renderButtonElId: string }) {
  const [isSignedIn, setIsSignedIn] = useRecoilState(isSignedInState);
  const [decodedToken, setDecodedToken] = useState({});

  useEffect(() => {
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
        }
      }

    }

    /* Initialize Google Sign-In */
    // @ts-expect-error google doesnt exist according to typescript
    window.google.accounts.id.initialize({
      client_id: "238164061415-sci0phqk4p0e2bj04dmcm430mu86lo5r.apps.googleusercontent.com",
      callback: handleCallbackResponse,
    });

    /* Render the Sign-In button */
    // @ts-expect-error google doesnt exist according to typescript
    window.google.accounts.id.renderButton(
      document.getElementById(renderButtonElId),
      { size: "medium", shape: "pill", text: "signin" }
    );
  }, [isSignedIn]);

  function googleLogout() {
    // clear cookie
    Cookies.remove("idToken");
    setIsSignedIn(false);
    document.getElementById(renderButtonElId)?.classList.remove("hidden");
  };


  function handleCallbackResponse(response) {
    document.getElementById(renderButtonElId)?.classList.add("hidden");

    const idToken = response.credential;
    setIsSignedIn(true);
    console.log("Encoded JWT ID Token:", idToken);

    // Store the token in a secure cookie
    Cookies.set("idToken", idToken, { expires: 1, secure: true, sameSite: "Strict" }); // Expires in 1 day
  }

  return (
    <div>
      {isSignedIn ?
        <UserProfile image={decodedToken.picture} onLogOut={googleLogout} />
        :
        <div id={renderButtonElId}></div>
      }
    </div>
  );
}

export default GoogleSignInButton;
