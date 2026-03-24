// src/store/rootSaga.ts
import { all, fork } from "redux-saga/effects";
import { watchRecordSaga } from "@/features/record/recordSaga";

export default function* rootSaga() {
  yield all([fork(watchRecordSaga)]);
}
