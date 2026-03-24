"use client";

import { useState } from "react";
import {
  Button,
  TextField,
  FormControl,
  InputLabel,
  Select,
  MenuItem,
} from "@mui/material";
import { useDispatch } from "react-redux";
import { AppDispatch } from "@/store/store";
import { RecActions } from "@/features/record/recordSlice";

export default function RecordSearch() {
  const dispatch = useDispatch<AppDispatch>();

  const [searchType, setSearchType] = useState("visitId");
  const [searchKeyword, setSearchKeyword] = useState("");
  const [startDate, setStartDate] = useState("");
  const [endDate, setEndDate] = useState("");

  const handleSearch = () => {
    if (searchType === "visitId") {
      const payload = {
        searchType,
        searchValue: searchKeyword.trim(),
      };

      dispatch(RecActions.searchRecordsRequest(payload));
      return;
    }

    const payload = {
      searchType,
      startDate,
      endDate,
    };

    dispatch(RecActions.searchRecordsRequest(payload));
  };

  const handleResetSearch = () => {
    setSearchType("visitId");
    setSearchKeyword("");
    setStartDate("");
    setEndDate("");

    dispatch(RecActions.fetchRecordsRequest());
  };

  return (
    <div style={{ display: "flex", gap: "8px", alignItems: "center" }}>
      <FormControl size="small" sx={{ minWidth: 110 }}>
        <InputLabel id="search-type-label">검색 기준</InputLabel>
        <Select
          labelId="search-type-label"
          label="검색 기준"
          value={searchType}
          onChange={(e) => setSearchType(e.target.value)}
        >
          <MenuItem value="visitId">진료 아이디</MenuItem>
          <MenuItem value="recordedAt">기록일시</MenuItem>
        </Select>
      </FormControl>

      {searchType === "visitId" ? (
        <TextField
          size="small"
          label="진료 아이디 입력"
          value={searchKeyword}
          onChange={(e) => setSearchKeyword(e.target.value)}
        />
      ) : (
        <>
          <TextField
            type="date"
            size="small"
            label="시작일"
            InputLabelProps={{ shrink: true }}
            value={startDate}
            onChange={(e) => setStartDate(e.target.value)}
          />
          <TextField
            type="date"
            size="small"
            label="종료일"
            InputLabelProps={{ shrink: true }}
            value={endDate}
            onChange={(e) => setEndDate(e.target.value)}
          />
        </>
      )}

      <Button variant="outlined" size="small" onClick={handleSearch}>
        검색
      </Button>

      <Button variant="text" size="small" onClick={handleResetSearch}>
        초기화
      </Button>
    </div>
  );
}