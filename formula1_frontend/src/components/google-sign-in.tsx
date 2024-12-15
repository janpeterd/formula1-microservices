import { useEffect, useState } from "react";
import { jwtDecode } from "jwt-decode";
import Cookies from "js-cookie";
import UserProfile from "./user-profile";

function GoogleSignInButton() {
  const [signedIn, setSignedIn] = useState(false);
  const [decodedToken, setDecodedToken] = useState({});

  useEffect(() => {
    const idToken = Cookies.get("idToken");
    if (idToken) {
      const decodedToken = jwtDecode(idToken);
      console.log("decodedToken", decodedToken)
      setDecodedToken(decodedToken);
      if (decodedToken?.exp) {
        const isExpired = Date.now() >= decodedToken.exp * 1000;
        if (isExpired) {
          console.log("Token expired. Prompt user to re-authenticate.");
          googleLogout();
        } else {
          console.log("Token is still valid.");
          setSignedIn(true);
        }
      }

    }
    /* Initialize Google Sign-In */
    window.google.accounts.id.initialize({
      client_id: "238164061415-sci0phqk4p0e2bj04dmcm430mu86lo5r.apps.googleusercontent.com",
      callback: handleCallbackResponse,
    });

    /* Render the Sign-In button */
    window.google.accounts.id.renderButton(
      document.getElementById("signInButton"),
      { size: "medium", shape: "pill", text: "signin" }
    );
  }, [signedIn]);

  function googleLogout() {
    // clear cookie
    console.log("LOGOUT");

    Cookies.remove("idToken");
    setSignedIn(false);
    document.getElementById("signInButton")?.classList.remove("hidden");
  };


  function handleCallbackResponse(response) {
    console.log("CALLBACK");
    document.getElementById("signInButton")?.classList.add("hidden");

    const idToken = response.credential;
    setSignedIn(true);
    console.log("Encoded JWT ID Token:", idToken);

    // Store the token in a secure cookie
    Cookies.set("idToken", idToken, { expires: 1, secure: true, sameSite: "Strict" }); // Expires in 1 day
  }

  return (
    <div>
      {signedIn ?
        <UserProfile image={decodedToken.picture} onLogOut={googleLogout} />
        :
        <div id="signInButton"></div>
      }
    </div>
  );
}

export default GoogleSignInButton;
