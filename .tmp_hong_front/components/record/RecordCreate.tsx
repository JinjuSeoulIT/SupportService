"use client";

import { useState } from "react";
import { useDispatch } from "react-redux";
import { useRouter } from "next/navigation";
import dayjs from "dayjs";
import type { AppDispatch } from "../../store/store";
import { RecActions } from "@/features/record/recordSlice";
import { RecordFormType } from "@/features/record/recordTypes";
import RecordForm from "./RecordForm";

const emptyForm: RecordFormType = {
  recordId: "",
  visitId: "",
  nursingId: "",
  recordedAt: "",
  createdAt: "",
  updatedAt: "",
  systolicBp: "",
  diastolicBp: "",
  pulse: "",
  respiration: "",
  temperature: "",
  spo2: "",
  observation: "",
  painScore: "",
  consciousnessLevel: "",
  initialAssessment: "",
  status: "",
};

const RecordCreate = () => {
  const [form, setForm] = useState<RecordFormType>(emptyForm);
  const dispatch = useDispatch<AppDispatch>();
  const router = useRouter();

  const handleSubmit = () => {
    
    // //여기 콘솔 확인
    // console.log("submit form =", form);

    const now = dayjs().format("YYYY-MM-DDTHH:mm:ss");

    const payload = {
      ...form,
      recordId: undefined,
      createdAt: now,
      updatedAt: now,
      status: form.status || "ACTIVE",
    };
    
    // 여기 콘솔 확인
    // console.log("submit payload =", payload);

    dispatch(RecActions.createRecordRequest(payload as RecordFormType));

    alert("간호 기록을 추가합니다.");
    router.push("/record/list");
  };

  return (
    <main style={{ padding: 24 }}>
      <RecordForm
        mode="create"
        form={form}
        onChange={setForm}
        onSubmit={handleSubmit}
      />
    </main>
  );
};

export default RecordCreate;