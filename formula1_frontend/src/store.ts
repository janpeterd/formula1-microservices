import { atom, RecoilState } from "recoil";

export const isSignedInState: RecoilState<boolean> = atom({
  key: "isSignedIn",
  default: false,
});
