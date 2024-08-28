import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import ApiService from '../../service/ApiService';

const EditProfilePage = () => {
    const [formData, setFormData] = useState({
        name: '',
        email: '',
        password: '',
        phoneNumber: ''
    });

    const [errorMessage, setErrorMessage] = useState('');
    const [successMessage, setSuccessMessage] = useState('');
    const [edit, setEdit] = useState('');
    const [user, setUser] = useState(null);
    const [error, setError] = useState(null);
    const [show, setShow] = useState(true);
    const navigate = useNavigate();

    useEffect(() => {
        const fetchUserProfile = async () => {
            try {
                const response = await ApiService.getUserProfile();
                setUser(response.user);
            } catch (error) {
                setError(error.message);
            }
        };

        fetchUserProfile();
    }, []);

    const handleDeleteProfile = async () => {
        if (!window.confirm('Are you sure you want to delete your account?')) {
            return;
        }
        try {
            await ApiService.deleteUser(user.id);
            ApiService.logout();
            navigate('/home');
        } catch (error) {
            setError(error.message);
        }
    };

    const handleInputChange = (e) => {
        const { name, value } = e.target;
        setFormData(prevState => ({
            ...prevState,
            [name]: value,
        }));
    };

    const validateForm = () => {
        const { name, email, password, phoneNumber } = formData;
        if (!name || !email || !password || !phoneNumber) {
            return false;
        }
        return true;
    };

    const handleSubmit = async (e) => {
        console.log(formData)
        e.preventDefault();
        if (!validateForm()) {
            setErrorMessage('Please fill all the fields.');
            setTimeout(() => setErrorMessage(''), 5000);
            return;
        }
        try {
            const response = await ApiService.updateUser(user.id, formData);
            console.log(response);

            // Check if the response is successful
            if (response.statusCode === 200) {
                setSuccessMessage('User details updated successfully');
                setTimeout(() => {
                    setSuccessMessage('');
                    navigate('/profile');
                }, 3000);
            }
        }
         catch (error) {
            setErrorMessage(error.response?.data?.message || error.message);
            setTimeout(() => setErrorMessage(''), 5000);
        }
    };

    const handleUpdateProfile = async () => {
        setEdit(true);
        setShow(false);
        
    }

    return (
        <div className="edit-profile-page">
            <h2>Edit Profile</h2>
            {error && <p className="error-message">{error}</p>}
            {show && user && (
                <div className="profile-details">
                    <div className='app'>
                    <div className="info">
                        <div><p><strong>Name:</strong> {user.name}</p></div>
                        <div><p><strong>Email:</strong> {user.email}</p></div>
                        <div><p><strong>Phone Number:</strong> {user.phoneNumber}</p></div>
                    </div>
                    </div>
                    <div className="button">
                    <div><button onClick={handleUpdateProfile}>Edit Details</button></div>
                    <div><button className="delete-profile-button" onClick={handleDeleteProfile}>Delete Profile</button></div>
                    </div>
                </div>
            )}
            {edit && (
                <div className='update'>
                <form onSubmit={handleSubmit}>
                <div className="form-group">
                    <label>Name:</label>
                    <input type="text" name="name" value={formData.name} onChange={handleInputChange} required />
                </div>
                <div className="form-group">
                    <label>Email:</label>
                    <input type="email" name="email" value={formData.email} onChange={handleInputChange} required />
                </div>
                <div className="form-group">
                    <label>Phone Number:</label>
                    <input type="text" name="phoneNumber" value={formData.phoneNumber} onChange={handleInputChange} required />
                </div>
                <div className="form-group">
                    <label>Password:</label>
                    <input type="password" name="password" value={formData.password} onChange={handleInputChange} required />
                </div>
                {errorMessage && <p className="error-message">{errorMessage}</p>}
                {successMessage && <p className="success-message">{successMessage}</p>}
                <button type="submit">Update</button>
            </form>
            </div>
            )}
        </div>
    );
};

export default EditProfilePage;
