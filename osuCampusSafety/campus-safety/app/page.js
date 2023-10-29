"use client"

import MapDisplay from "./components/MapDisplay"
import Header from "./components/Header"
import Graphs from "./components/Graphs";
import { useState } from "react";


// const crimeData = [
//   {
//   "Case Number": "P2023-03286",
//   "Date/Time Reported": "10/26/23 15:17",
//   "Date/Time Occurred Start": "10/26/23 07:30",
//   "Date/Time Occurred End": "10/26/23 15:15",
//   "Offenses": "Theft - without consent",
//   "General Location": "Carmack Lot 5",
//   "Disposition": "Closed"
//   },
//   {
//   "Case Number": "P2023-03280",
//   "Date/Time Reported": "10/26/23 13:38",
//   "Date/Time Occurred Start": "10/26/23 13:38",
//   "Date/Time Occurred End": null,
//   "Offenses": "Theft - without consent; Assault - knowingly harm victim",
//   "General Location": "The Union",
//   "Disposition": "Open - Arrest"
//   },
//   {
//   "Case Number": "CSA2023-03285",
//   "Date/Time Reported": "10/26/23 10:13",
//   "Date/Time Occurred Start": "10/10/23 09:30",
//   "Date/Time Occurred End": null,
//   "Offenses": "CSA Report: Stalking",
//   "General Location": "Baker Hall - West",
//   "Disposition": "Closed"
//   },
//   {
//   "Case Number": "P2023-03276",
//   "Date/Time Reported": "10/26/23 08:37",
//   "Date/Time Occurred Start": "08/01/23 00:01",
//   "Date/Time Occurred End": "10/25/23 15:00",
//   "Offenses": "Theft - without consent",
//   "General Location": "Hitchcock Hall",
//   "Disposition": "Closed"
//   },
//   {
//   "Case Number": "P2023-03274",
//   "Date/Time Reported": "10/26/23 05:44",
//   "Date/Time Occurred Start": "10/25/23 16:30",
//   "Date/Time Occurred End": "10/26/23 06:00",
//   "Offenses": "Criminal Damaging/Endangering - knowingly any means",
//   "General Location": "Maintenance Building",
//   "Disposition": "Open - Pending Investigation"
//   },
//   {
//   "Case Number": "EXT2023-03284",
//   "Date/Time Reported": "10/26/23 02:44",
//   "Date/Time Occurred Start": "10/24/23 21:48",
//   "Date/Time Occurred End": null,
//   "Offenses": "Public Indecency - exposure",
//   "General Location": "E 11TH AVENUE",
//   "Disposition": "Closed"
//   },
//   {
//   "Case Number": "P2023-03273",
//   "Date/Time Reported": "10/26/23 01:28",
//   "Date/Time Occurred Start": "10/26/23 01:28",
//   "Date/Time Occurred End": null,
//   "Offenses": "Offenses Involving Underage Persons - underage consume beer intoxicating liquor; Disorderly Conduct - intoxicated create risk of harm",
//   "General Location": "Morrill Tower",
//   "Disposition": "Closed"
//   },
//   {
//   "Case Number": "P2023-03272",
//   "Date/Time Reported": "10/25/23 23:39",
//   "Date/Time Occurred Start": "10/25/23 23:39",
//   "Date/Time Occurred End": "10/26/23 23:56",
//   "Offenses": "Domestic Violence (CCC) Knowingly Intimate Partner",
//   "General Location": "Schottenstein Center",
//   "Disposition": "Open - Pending Investigation"
//   }
// ]




export default function Page() {
  const [isOpenChecked, setIsOpenChecked] = useState(true);
  const [isClosedChecked, setIsClosedChecked] = useState(true);
  const [isCrimeTypeChecked, setCrimeTypeChecked] = useState([false, false, false, false, false]);


  let crimeData = require('./_data/crimeData.json')

  crimeData = filterByDisposition(isOpenChecked, isClosedChecked, crimeData)
  let commonCrimeFilter = getMostCommonCrimes(isCrimeTypeChecked)
  crimeData = filterByCrime(commonCrimeFilter, crimeData)

  let crimeLatLongs = getCrimeLocationCounts(crimeData)
  
  
  return <>
    <Header></Header>
    <MapDisplay 
    crimeData={crimeData} 
    crimeLatLongs={crimeLatLongs}
    isOpenChecked={isOpenChecked} 
    setIsOpenChecked={setIsOpenChecked}
    isClosedChecked={isClosedChecked}
    setIsClosedChecked={setIsClosedChecked}
    isCrimeTypeChecked={isCrimeTypeChecked}
    setCrimeTypeChecked={setCrimeTypeChecked}/>
    <Graphs/>
  </>
}


const getCrimeLocationCounts = (crimeData) => {
  let crimeLocationCounts = {}
  
  crimeData.forEach(crime => {
    let s = crime["location"]
    if(s in crimeLocationCounts) {
      crimeLocationCounts[s]++
    } else {
      crimeLocationCounts[s] = 1
    }
  });

  

  return Object.entries(crimeLocationCounts).map(([location, count]) => {
    return {latLong: getCrimeLatLong(location), count}
  }).filter(a => a.latLong != undefined)
}


const getCrimeLatLong = (crime) => {
  const locationDict = require('./_data/latlong.json')
  
  return locationDict[crime]
}

const filterByDisposition = (open = true, closed = true, crimeData) => {
  return crimeData.filter(crime => {
    if(open && crime["disposition"] == "open") {
      return true
    }
    if(closed && crime["disposition"] == "closed") {
      return true
    }

    return false
  })
}

const filterByCrime = (typeFilters, crimeData) => {
  let typeFiltersSet = new Set(typeFilters)
  if(typeFilters.length == 0) {
    return crimeData
  }
  return crimeData.filter(crime => {
    const crimeList = crime["crimes"]
    for(const c of crimeList) {
      if(typeFiltersSet.has(c)) {
        return true
      }
    }
    return false
  })
}


const getMostCommonCrimes = (crimeTypeChecked) => {
  let commonCrimes = ["theft", "drugs", "disorderly conduct", "criminal trespass", "illegal use or possession"]
  let filtered = commonCrimes.filter((_, i) => crimeTypeChecked[i])
  console.log("filteredASHDAKJNSDAJDNKA", filtered, crimeTypeChecked)
  return filtered
}

