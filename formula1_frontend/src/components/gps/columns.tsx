"use client"

import { Pencil } from "lucide-react"
import GpResponse from "@/dto/gpResponse"
import { ColumnDef } from "@tanstack/react-table"
import { DataTableColumnHeader } from "./colum-headers"
import { Button } from "../ui/button"
import DeleteButton from "./delete-button"
export const columns: ColumnDef<GpResponse>[] = [
  {
    id: "name",
    accessorKey: "name",
    header: ({ column }) => (
      <DataTableColumnHeader column={column} title="Name" className="w-[185px]" />
    ),
  },
  {
    id: "country",
    accessorKey: "country",
    header: ({ column }) => (
      <DataTableColumnHeader column={column} title="Country" />
    ),
  },
  {
    id: "city",
    accessorKey: "city",
    header: ({ column }) => (
      <DataTableColumnHeader column={column} title="City" />
    ),
  },
  {
    id: "distanceMeters",
    accessorKey: "distanceMeters",
    header: ({ column }) => (
      <DataTableColumnHeader column={column} title="Distance" className="w-[80px]" />
    ),
  },
  {
    id: "laps",
    accessorKey: "laps",
    header: ({ column }) => (
      <DataTableColumnHeader column={column} title="Laps" />
    ),
  },
  {
    id: "winningDriverFullName", // Add id to the column
    accessorFn: (row) => `${row.winningDriver.firstName} ${row.winningDriver.lastName}`,
    header: ({ column }) => (
      <DataTableColumnHeader column={column} title="winningDriver" />
    ),
  },
  {
    id: "secondDriverFullName", // Add id to the column
    accessorFn: (row) => `${row.secondDriver.firstName} ${row.secondDriver.lastName}`,
    header: ({ column }) => (
      <DataTableColumnHeader column={column} title="SecondDriver" />
    ),
  },
  {
    id: "thirdDriverFullName", // Add id to the column
    accessorFn: (row) => `${row.thirdDriver.firstName} ${row.thirdDriver.lastName}`,
    header: ({ column }) => (
      <DataTableColumnHeader column={column} title="ThirdDriver" />
    ),
  },
  {
    id: "winningTeam.name",
    accessorKey: "winningTeam.name",
    header: ({ column }) => (
      <DataTableColumnHeader column={column} title="WinningTeam" />
    ),
  },
  {
    id: "raceDate",
    accessorKey: "raceDate",
    header: ({ column }) => (
      <DataTableColumnHeader column={column} title="RaceDate" />
    ),
  },
  {
    id: "imageUrl", // Add id to the column
    accessorKey: "imageUrl",
    header: ({ column }) => (
      <DataTableColumnHeader column={column} title="ImageUrl" />
    ),
    cell: ({ row }) => (
      <img src={`http://localhost:8084/${row.getValue("imageUrl")}`} alt="Image" style={{ width: "100px", height: "auto" }} />
    ),
  },
  {
    id: "trackImageUrl", // Add id to the column
    accessorKey: "trackImageUrl",
    header: ({ column }) => (
      <DataTableColumnHeader column={column} title="TrackImageUrl" />
    ),
    cell: ({ row }) => (
      <img src={`http://localhost:8084/${row.getValue("trackImageUrl")}`} alt="Track Image" style={{ width: "100px", height: "auto" }} />
    ),
  },
  {
    id: "edit",
    header: () => (
      <div className="text-center">Actions</div>
    ),
    cell: ({ row, table }) => {
      const gpCode = row.original.gpCode; // Ensure `gpCode` is accessible in your data

      return (
        <div className="flex flex-col justify-center items-center gap-y-0.5">
          <Button onClick={() => table.options.meta?.navigate(`/grand-prix-add/${row.original.gpCode}`)} className="bg-dark px-3 py-1">
            <Pencil />
          </Button>
          <DeleteButton gpCode={gpCode} row={row} table={table} />
        </div>
      );
    },
  },
]
