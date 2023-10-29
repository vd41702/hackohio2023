"use client"
import { useState } from 'react';

function DispositionFilter({isOpenChecked, setIsOpenChecked, isClosedChecked, setIsClosedChecked}) {
  // const [isOpenChecked, setIsOpenChecked] = useState(false);
  // const [isClosedChecked, setIsClosedChecked] = useState(false);

  const handleOpenChange = () => {
    setIsOpenChecked(!isOpenChecked);
  };

  const handleClosedChange = () => {
    setIsClosedChecked(!isClosedChecked);
  };

  return (
    <div className="space-y-2">
      <h4 className='text-lg'>Open/Closed Crimes</h4>
      <label className="flex items-center space-x-2">
        <input
          type="checkbox"
          checked={isOpenChecked}
          onChange={handleOpenChange}
          className="form-checkbox text-indigo-600 h-5 w-5"
        />
        <span className="text-indigo-800">Open</span>
      </label>
      <label className="flex items-center space-x-2">
        <input
          type="checkbox"
          checked={isClosedChecked}
          onChange={handleClosedChange}
          className="form-checkbox text-red-600 h-5 w-5"
        />
        <span className="text-red-800">Closed</span>
      </label>
    </div>
  );
}

export default DispositionFilter;