import React, { useState, useEffect } from 'react';
import DatePicker from 'react-datepicker';
import 'react-datepicker/dist/react-datepicker.css';
import ApiService from '../../service/ApiService';

const RoomSearch = ({ handleSearchResult }) => {
  const [startDate, setStartDate] = useState(null);
  const [endDate, setEndDate] = useState(null);
  const [roomType, setRoomType] = useState('');
  const [roomTypes, setRoomTypes] = useState([]);
  const [error, setError] = useState('');
  const [loading, setLoading] = useState(false);

  useEffect(() => {
    const fetchRoomTypes = async () => {
      setLoading(true);
      try {
        const types = await ApiService.getRoomTypes();
        setRoomTypes(types);
        if (types.length === 0) {
          showError('No room types available at the moment.');
        }
      } catch (error) {
        console.error('Error fetching room types: ', error.message);
        showError('Failed to fetch room types. Please try again later.');
      } finally {
        setLoading(false);
      }
    };
    fetchRoomTypes();
  }, []);

  const showError = (message, timeout = 5000) => {
    setError(message);
    setTimeout(() => {
      setError('');
    }, timeout);
  };

  const handleInternalSearch = async () => {
    if (!startDate || !endDate || !roomType) {
      showError('Please select all fields');
      return;
    }
    setLoading(true);
    try {
      const formattedCheckInDate = new Date(startDate.getTime() - (startDate.getTimezoneOffset() * 60000)).toISOString().split('T')[0];
      const formattedCheckOutDate = new Date(endDate.getTime() - (endDate.getTimezoneOffset() * 60000)).toISOString().split('T')[0];

      console.log('Formatted Check-in Date:', formattedCheckInDate);
      console.log('Formatted Check-out Date:', formattedCheckOutDate);

      const response = await ApiService.getAvailableRoomsByDateAndType(formattedCheckInDate, formattedCheckOutDate, roomType);

      if (response.statusCode === 200) {
        if (response.roomList.length === 0) {
          showError('Room not currently available for this date range on the selected room type.');
          return;
        }
        handleSearchResult(response.roomList);
        setError('');
        setStartDate(null);
        setEndDate(null);
        setRoomType('');
      }
    } catch (error) {
      showError('Unknown error occurred: ' + (error.response?.data?.message || error.message));
    } finally {
      setLoading(false);
    }
  };

  return (
      <section>
        <div className="search-container">
          <div className="search-field">
            <label>Check-in Date</label>
            <DatePicker
                selected={startDate}
                onChange={(date) => setStartDate(date)}
                dateFormat="dd/MM/yyyy"
                placeholderText="Select Check-in Date"
            />
          </div>
          <div className="search-field">
            <label>Check-out Date</label>
            <DatePicker
                selected={endDate}
                onChange={(date) => setEndDate(date)}
                dateFormat="dd/MM/yyyy"
                placeholderText="Select Check-out Date"
            />
          </div>

          <div className="search-field">
            <label>Room Type</label>
            <select value={roomType} onChange={(e) => setRoomType(e.target.value)}>
              <option disabled value="">
                Select Room Type
              </option>
              {roomTypes.map((roomType) => (
                  <option key={roomType} value={roomType}>
                    {roomType}
                  </option>
              ))}
            </select>
          </div>
          <button className="home-search-button" onClick={handleInternalSearch} disabled={loading}>
            {loading ? 'Searching...' : 'Search Rooms'}
          </button>
        </div>
        {error && <p className="error-message">{error}</p>}
      </section>
  );
};

export default RoomSearch;