// "use client";

// import { useEffect } from "react";
// import {
//   Alert,
//   Box,
//   CircularProgress,
//   Paper,
//   Table,
//   TableBody,
//   TableCell,
//   TableHead,
//   TableRow,
//   Typography,
//   Chip,
//   Button,
// } from "@mui/material";
// import { useDispatch, useSelector } from "react-redux";
// import { AppDispatch } from "@/store/store";
// import { RootState } from "@/store/rootReducer";
// import { RecActions } from "@/features/record/recordSlice";
// import { useRouter } from "next/navigation";
// import Link from "next/link";
// import RecordSearch from "./RecordSearch";
// import dayjs from "dayjs";

// const formatDateTime = (value: string) => {
//   if (!value) return "-";
//   return dayjs(value).format("YYYY-MM-DD HH:mm:ss");
// };

// export default function RecordList() {
//   const dispatch = useDispatch<AppDispatch>();
//   const { list, loading, error } = useSelector(
//     (state: RootState) => state.records
//   );
//   const router = useRouter();

//   useEffect(() => {
//     dispatch(RecActions.fetchRecordsRequest());
//   }, [dispatch]);

//   return (
//     <Box sx={{ p: 4 }}>
//       <Box
//         sx={{
//           display: "flex",
//           justifyContent: "space-between",
//           alignItems: "center",
//           mb: 3,
//         }}
//       >
//         <Typography variant="h5" fontWeight={700}>
//           간호 기록
//         </Typography>

//         <RecordSearch />

//         <Button
//           component={Link}
//           href="/record/create"
//           variant="contained"
//           size="small"
//         >
//           간호 기록 등록
//         </Button>
//       </Box>

//       {loading && <CircularProgress />}
//       {error && <Alert severity="error">{error}</Alert>}

//       {!loading && !error && (
//         <Paper elevation={1} sx={{ borderRadius: 3, overflow: "hidden" }}>
//           <Table>
//             <TableHead>
//               <TableRow sx={{ backgroundColor: "#f5f5f5" }}>
//                 <TableCell sx={{ fontWeight: 700 }}>간호 기록 아이디</TableCell>
//                 <TableCell sx={{ fontWeight: 700 }}>간호사 아이디</TableCell>
//                 <TableCell sx={{ fontWeight: 700 }}>진료 아이디</TableCell>
//                 <TableCell sx={{ fontWeight: 700 }}>기록일시</TableCell>
//                 {/* <TableCell sx={{ fontWeight: 700 }}>상태</TableCell> */}
//                 <TableCell sx={{ fontWeight: 700 }}>생성일시</TableCell>
//                 {/* <TableCell sx={{ fontWeight: 700 }}>수정일시</TableCell> */}
//               </TableRow>
//             </TableHead>

//             <TableBody>
//               {list.length === 0 && (
//                 <TableRow>
//                   <TableCell colSpan={7} align="center">
//                     데이터가 없습니다.
//                   </TableCell>
//                 </TableRow>
//               )}

//               {list.map((row) => (
//                 <TableRow
//                   key={row.recordId}
//                   hover
//                   sx={{ cursor: "pointer" }}
//                   onClick={() => router.push(`/record/detail/${row.recordId}`)}
//                 >
//                   <TableCell>{row.recordId ?? "-"}</TableCell>
//                   <TableCell>{row.nursingId ?? "-"}</TableCell>
//                   <TableCell>{row.visitId ?? "-"}</TableCell>
//                   <TableCell>{formatDateTime(row.recordedAt)}</TableCell>

//                   {/* <TableCell>
//                     {row.status === "ACTIVE" ? (
//                       <Chip label="ACTIVE" size="small" color="success" />
//                     ) : (
//                       <Chip label={row.status ?? "-"} size="small" />
//                     )}
//                   </TableCell> */}

//                   <TableCell>{formatDateTime(row.createdAt)}</TableCell>
//                   {/* <TableCell>{formatDateTime(row.updatedAt)}</TableCell> */}
//                 </TableRow>
//               ))}
//             </TableBody>
//           </Table>
//         </Paper>
//       )}
//     </Box>
//   );
// }



"use client";

import { useEffect } from "react";
import {
  Alert,
  Box,
  CircularProgress,
  Paper,
  Table,
  TableBody,
  TableCell,
  TableHead,
  TableRow,
  Typography,
  Chip,
  Button,
  Divider,
  TableContainer,
  Stack,
} from "@mui/material";
import { useDispatch, useSelector } from "react-redux";
import { AppDispatch } from "@/store/store";
import { RootState } from "@/store/rootReducer";
import { RecActions } from "@/features/record/recordSlice";
import { useRouter } from "next/navigation";
import Link from "next/link";
import RecordSearch from "./RecordSearch";
import dayjs from "dayjs";

const formatDateTime = (value: string) => {
  if (!value) return "-";
  return dayjs(value).format("YYYY-MM-DD HH:mm:ss");
};

export default function RecordList() {
  const dispatch = useDispatch<AppDispatch>();
  const { list, loading, error } = useSelector(
    (state: RootState) => state.records
  );
  const router = useRouter();

  useEffect(() => {
    dispatch(RecActions.fetchRecordsRequest());
  }, [dispatch]);

  return (
    <Box
      sx={{
        px: 3,
        py: 3,
        maxWidth: 1240,
        mx: "auto",
      }}
    >
      <Paper
        elevation={2}
        sx={{
          borderRadius: 3,
          overflow: "hidden",
          border: "1px solid",
          borderColor: "grey.200",
          backgroundColor: "#fff",
        }}
      >
        {/* 상단 헤더 영역 */}
        <Box
          sx={{
            px: 3,
            py: 2.5,
            backgroundColor: "#fafafa",
          }}
        >
          <Box
            sx={{
              display: "flex",
              justifyContent: "space-between",
              alignItems: { xs: "flex-start", lg: "center" },
              gap: 2,
              flexWrap: "wrap",
            }}
          >
            <Box sx={{ minWidth: 220, flex: "1 1 260px" }}>
              <Typography variant="h6" fontWeight={700}>
                간호 기록
              </Typography>
              <Typography
                variant="body2"
                color="text.secondary"
                sx={{
                  mt: 0.5,
                  whiteSpace: "nowrap",
                }}
              >
                간호 기록 목록을 조회하고 상세 페이지로 이동할 수 있습니다.
              </Typography>
            </Box>

            <Box
              sx={{
                flex: "1 1 700px",
                minWidth: 320,
                display: "flex",
                justifyContent: { xs: "flex-start", lg: "flex-end" },
                alignItems: "center",
                gap: 1.5,
                flexWrap: "wrap",
              }}
            >
              <RecordSearch />

              <Button
                component={Link}
                href="/record/create"
                variant="contained"
                size="small"
                sx={{
                  whiteSpace: "nowrap",
                  borderRadius: 2,
                  px: 1.75,
                  height: 36,
                  flexShrink: 0,
                }}
              >
                간호 기록 등록
              </Button>
            </Box>
          </Box>
        </Box>

        <Divider />

        {/* 상태 영역 */}
        <Box sx={{ p: 2.5 }}>
          {loading && (
            <Box sx={{ display: "flex", justifyContent: "center", py: 5 }}>
              <CircularProgress size={28} />
            </Box>
          )}

          {error && (
            <Alert severity="error" sx={{ mb: 2 }}>
              {error}
            </Alert>
          )}

          {!loading && !error && (
            <Paper
              elevation={0}
              sx={{
                borderRadius: 2,
                border: "1px solid",
                borderColor: "grey.200",
                overflow: "hidden",
              }}
            >
              <TableContainer>
                <Table
                  size="small"
                  stickyHeader
                  sx={{
                    minWidth: 920,
                  }}
                >
                  <TableHead>
                    <TableRow>
                      <TableCell
                        sx={{
                          fontWeight: 700,
                          py: 1.4,
                          backgroundColor: "#f8f9fa",
                          whiteSpace: "nowrap",
                        }}
                      >
                        간호 기록 아이디
                      </TableCell>
                      <TableCell
                        sx={{
                          fontWeight: 700,
                          py: 1.4,
                          backgroundColor: "#f8f9fa",
                          whiteSpace: "nowrap",
                        }}
                      >
                        간호사 아이디
                      </TableCell>
                      <TableCell
                        sx={{
                          fontWeight: 700,
                          py: 1.4,
                          backgroundColor: "#f8f9fa",
                          whiteSpace: "nowrap",
                        }}
                      >
                        진료 아이디
                      </TableCell>
                      <TableCell
                        sx={{
                          fontWeight: 700,
                          py: 1.4,
                          backgroundColor: "#f8f9fa",
                          whiteSpace: "nowrap",
                        }}
                      >
                        기록일시
                      </TableCell>

                      {/* 원래 코드 유지: 상태 컬럼 필요할 때 바로 살릴 수 있게 둠 */}
                      {/* <TableCell
                        sx={{
                          fontWeight: 700,
                          py: 1.2,
                          backgroundColor: "#f8f9fa",
                          whiteSpace: "nowrap",
                        }}
                      >
                        상태
                      </TableCell> */}

                      <TableCell
                        sx={{
                          fontWeight: 700,
                          py: 1.4,
                          backgroundColor: "#f8f9fa",
                          whiteSpace: "nowrap",
                        }}
                      >
                        생성일시
                      </TableCell>

                      {/* 원래 코드 유지: 수정일시 필요할 때 바로 살릴 수 있게 둠 */}
                      {/* <TableCell
                        sx={{
                          fontWeight: 700,
                          py: 1.2,
                          backgroundColor: "#f8f9fa",
                          whiteSpace: "nowrap",
                        }}
                      >
                        수정일시
                      </TableCell> */}
                    </TableRow>
                  </TableHead>

                  <TableBody>
                    {list.length === 0 && (
                      <TableRow>
                        <TableCell colSpan={5} align="center" sx={{ py: 5 }}>
                          데이터가 없습니다.
                        </TableCell>
                      </TableRow>
                    )}

                    {list.map((row) => (
                      <TableRow
                        key={row.recordId}
                        hover
                        sx={{
                          cursor: "pointer",
                          "& td": {
                            py: 1.25,
                          },
                          "&:hover": {
                            backgroundColor: "#f9fbff",
                          },
                        }}
                        onClick={() =>
                          router.push(`/record/detail/${row.recordId}`)
                        }
                      >
                        <TableCell>{row.recordId ?? "-"}</TableCell>
                        <TableCell>{row.nursingId ?? "-"}</TableCell>
                        <TableCell>{row.visitId ?? "-"}</TableCell>
                        <TableCell>{formatDateTime(row.recordedAt)}</TableCell>

                        {/* 원래 코드 유지: 상태 컬럼 필요할 때 바로 사용 가능 */}
                        {/* <TableCell>
                          {row.status === "ACTIVE" ? (
                            <Chip
                              label="ACTIVE"
                              size="small"
                              color="success"
                            />
                          ) : (
                            <Chip
                              label={row.status ?? "-"}
                              size="small"
                              color="default"
                            />
                          )}
                        </TableCell> */}

                        <TableCell>{formatDateTime(row.createdAt)}</TableCell>

                        {/* 원래 코드 유지: 수정일시 필요할 때 바로 사용 가능 */}
                        {/* <TableCell>{formatDateTime(row.updatedAt)}</TableCell> */}
                      </TableRow>
                    ))}
                  </TableBody>
                </Table>
              </TableContainer>
            </Paper>
          )}
        </Box>
      </Paper>
    </Box>
  );
}