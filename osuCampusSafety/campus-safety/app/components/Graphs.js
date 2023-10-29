import Image from "next/image"

const Graphs = () => {
    return <div className="flex gap-10 justify-center">
        <Image src={"/CrimeVSDates.png"} width={400} height={400} alt="graph"/>
        <Image src={"/CrimeVSHour.png"} width={400} height={400} alt="graph"/>
    </div>
}

export default Graphs