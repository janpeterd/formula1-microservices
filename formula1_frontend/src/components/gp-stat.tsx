function GrandPrixStat({ label, stat }: { label: string, stat: string }) {
  return (
    <div className="flex flex-col border-neutral-300 border-r-2 border-b-2 rounded-br-2xl p-8 justify-center">
      <div className="pr-12">
        {label}
      </div>
      <div className="font-f1 text-2xl font-bold pr-12">
        {stat}
      </div>
    </div>
  )
}

export default GrandPrixStat;
