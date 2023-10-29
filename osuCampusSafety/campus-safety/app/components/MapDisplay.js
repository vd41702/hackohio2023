"use client"

import MapOptions from "./MapOptions";
import { env } from "@/next.config";
import Heatmap from "./Heatmap";



const MapDisplay = ({ crimeData, crimeLatLongs, isOpenChecked, setIsOpenChecked, isClosedChecked, setIsClosedChecked, isCrimeTypeChecked, setCrimeTypeChecked}) => {

  return (
    <div className="flex gap-5 h-80vh p-10">
      <div className="w-200 max-w-200 rounded">
        <MapOptions
          crimeData={crimeData}
          isOpenChecked={isOpenChecked}
          setIsOpenChecked={setIsOpenChecked}
          isClosedChecked={isClosedChecked}
          setIsClosedChecked={setIsClosedChecked}
          isCrimeTypeChecked={isCrimeTypeChecked}
          setCrimeTypeChecked={setCrimeTypeChecked} />
      </div>
      <div className="flex-grow h-auto w-auto">
        <Heatmap 
        crimeLatLongs={crimeLatLongs}
        isOpenChecked={isOpenChecked}
        isClosedChecked={isClosedChecked}
        isCrimeTypeChecked={isCrimeTypeChecked}/>
      </div>
    </div>)

}

//   <div className="flex gap-5 p-10 justify-center">

//     <div className="flex-1 min-w-[300px] min-h-[300px]">
//     </div>

//     {/* <iframe
//         width="600"
//         loading="lazy"
//         allowFullScreen
//         referrerPolicy="no-referrer-when-downgrade"
//         src={`https://www.google.com/maps/embed/v1/view?key=${process.env.NEXT_PUBLIC_GOOGLE_MAPS_API_KEY}&center=40.000317218522056,-83.01565528751004&zoom=15`}>
//     </iframe> */}

// </div>;
// }


export default MapDisplay



