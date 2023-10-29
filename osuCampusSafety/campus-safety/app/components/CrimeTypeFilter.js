"use client"
import { useState } from 'react';

function CrimeTypeFilter({isCrimeTypeChecked, setCrimeTypeChecked}) {
  
    const handleCrimeTypeChange = (index) => {
        const updatedCrimeTypes = [...isCrimeTypeChecked];
        updatedCrimeTypes[index] = !updatedCrimeTypes[index];
        setCrimeTypeChecked(updatedCrimeTypes);
    }
    const commonCrimes = ["theft", "drugs", "disorderly conduct", "criminal trespass", "illegal use or possession"]

    return (
        <div>
            <div className='mt-5 text-lg'>Crime Types</div>
            <div>
            {commonCrimes.map((crime, i) => (
                <label className="flex items-center space-x-2" key={i}>
                    <input
                    type="checkbox"
                    checked={isCrimeTypeChecked[i]}
                    onChange={() => handleCrimeTypeChange(i)}
                    className="form-checkbox text-indigo-600 h-5 w-5"
                    />
                    <span className="text-indigo-800">{toTitleCase(crime)}</span>
                </label>
))}
            </div>
        </div>
    );
}


const toTitleCase = (str) => {
    return str.replace(/\w\S*/g, (text) => {
        return text.charAt(0).toUpperCase() + text.substr(1).toLowerCase();
    });
}

export default CrimeTypeFilter;