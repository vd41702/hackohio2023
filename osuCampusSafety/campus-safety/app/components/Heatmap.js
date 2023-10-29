"use client"
import React, { useEffect } from 'react';
import Script from 'next/script';


const Heatmap = ({ crimeLatLongs, isOpenChecked, isClosedChecked, isCrimeTypeChecked}) => {

    useEffect(() => {
        if (typeof window != "undefined" && window.google != undefined) {
            window.initMap()
        }
    }, [crimeLatLongs])
    
    if (typeof window != "undefined") {

        window.initMap = () => {
            let map = new google.maps.Map(document.getElementById('map'), {
                center: { lat: 40.000317218522056, lng: -83.01565528751004 },
                zoom: 15,
            });


            let temp = crimeLatLongs.map(crime => {
                let latlong = crime["latLong"]
                let [lat, long] = latlong.split(',')
                return { lat: parseFloat(lat), long: parseFloat(long), count: crime["count"] }
            })


            // Prepare data for the heatmap
            let heatmapData = temp.map((crime) => {
                return { location: new google.maps.LatLng(crime.lat, crime.long), weight: crime["count"] * 100 }
            })

            // Create the heatmap layer
            let heatmap = new google.maps.visualization.HeatmapLayer({
                data: heatmapData,
                radius: 50,
                dissipating: true,
            });

            heatmap.setMap(map)
        }
    }







    return <>
        <div id="map" className='flex-grow h-full w-auto'/>
        <Script src={`https://maps.googleapis.com/maps/api/js?key=${process.env.NEXT_PUBLIC_GOOGLE_MAPS_API_KEY}&libraries=visualization&callback=initMap`} strategy="afterInteractive" onLoad={() => initMap()} />
    </>;
};




export default Heatmap;
