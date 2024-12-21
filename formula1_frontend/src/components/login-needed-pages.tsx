import GoogleSignInButton from "./google-sign-in";

function LoginNeededPage() {
  return (
    <div className="container mx-auto min-h-[60%] flex flex-col gap-y-8 justify -center items-center">
      <h1 className="text-3xl font-f1 font-bold italic text-center">You need to login to access this page!</h1>
      <img className="max-h-96 rounded-lg" src="/f1_image.avif" alt="F1 race" />
      <div className="py-4 bg-accent w-full flex justify-center items-center">
        <GoogleSignInButton renderButtonElId="pageSignInButton" />
      </div>
    </div>
  )
}

export default LoginNeededPage;
