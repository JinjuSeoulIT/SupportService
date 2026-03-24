// src/store/rootReducer.ts
import { combineReducers } from "@reduxjs/toolkit";
import recordsReducer from "../features/record/recordSlice";

const rootReducer = combineReducers({
  records : recordsReducer
});

export type RootState = ReturnType<typeof rootReducer>;
export default rootReducer;
