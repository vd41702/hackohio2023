import DispositionFilter from "./DispositionFilter"
import CrimeTypeFilter from "./CrimeTypeFilter"

const MapOptions = ({crimeData, isOpenChecked, setIsOpenChecked, isClosedChecked, setIsClosedChecked, isCrimeTypeChecked, setCrimeTypeChecked}) => {
    return <div className="border rounded p-5">
        <h1 className="text-center mb-5 text-lg font-bold">Map Settings</h1>
        <DispositionFilter
        isOpenChecked={isOpenChecked} 
        setIsOpenChecked={setIsOpenChecked}
        isClosedChecked={isClosedChecked}
        setIsClosedChecked={setIsClosedChecked}/>
        <CrimeTypeFilter 
        commonCrimes={getMostCommonCrimes(crimeData)}
        isCrimeTypeChecked={isCrimeTypeChecked}
        setCrimeTypeChecked={setCrimeTypeChecked}/>
        </div>
}

const getMostCommonCrimes = (crimeData) => {
    const crimeCounts = {}
    crimeData.map(crime => {
        crimeCounts[crime["crimes"][0]] = (crimeCounts[crime["crimes"][0]] || 0) + 1
    })
    
    const crimeCountsArray = Object.keys(crimeCounts).map((crime) => ({
        crime,
        count: crimeCounts[crime],
      }));

    return crimeCountsArray.filter(crime => crime.crime != "unknown")
    .sort((a, b) => b.count - a.count)
    .slice(0, 5)
}


export default MapOptions