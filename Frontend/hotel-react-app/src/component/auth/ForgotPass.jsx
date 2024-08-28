import React, { useState } from 'react';
import ApiService from '../../service/ApiService';

const ForgotPass = () => {
    const [email, setEmail] = useState('');
    const [otp, setOtp] = useState('');
    const [password, setPassword] = useState('');
    const [message, setMessage] = useState('');
    const [error, setError] = useState('');
    const [step, setStep] = useState(1);

    const handleEmailSubmit = async (e) => {
        e.preventDefault();
        try {
            const response = await ApiService.verifyMail(email);
            setMessage(response);
            setTimeout(() => setMessage(''), 5000);
            setStep(2);
        } catch (error) {
            setError(error.response.data);
            setTimeout(() => setError(''), 5000);
            return;
        }
    };

    const handleOtpSubmit = async (e) => {
        e.preventDefault();
        try {
            const response = await ApiService.verifyOtp(otp);
            setMessage(response);
            setTimeout(() => setMessage(''), 5000);
            setStep(3);
        } catch (error) {
            setError(error.response.data);
            setTimeout(() => setError(''), 5000);
            return;
        }
    };

    const handlePasswordSubmit = async (e) => {
        e.preventDefault();
        try {
            const response = await ApiService.changePassword(otp, password);
            setMessage(response);
            window.location.href = '/login'
        } catch (error) {
            setError(error.response.data);
            setTimeout(() => setError(''), 5000);
            return;
        }
    };

    return (
        <div>
            {step === 1 && (
                <div className="auth-container">
                    <h2>Verify Email</h2>
                    <div>
                    <form onSubmit={handleEmailSubmit}>
                        <div className="form-group">
                        <label>Email: </label>
                        <input
                            type="email"
                            value={email}
                            onChange={(e) => setEmail(e.target.value)}
                            placeholder="Enter your email"
                            required
                        />
                        </div>
                        <button type="submit">Send OTP</button>
                    </form>
                </div>
                </div>
            )}
            {step === 2 && (
                <div className="auth-container">
                    <h2>Verify OTP</h2>
                    <form onSubmit={handleOtpSubmit}>
                    <div className="form-group">
                    <label>OTP: </label>
                        <input
                            type="text"
                            value={otp}
                            onChange={(e) => setOtp(e.target.value)}
                            placeholder="Enter OTP"
                            required
                        />
                        </div>
                        <button type="submit">Verify OTP</button>
                    </form>
                </div>
            )}
            {step === 3 && (
                <div className="auth-container">
                    <h2>Change Password</h2>
                    <form onSubmit={handlePasswordSubmit}>
                    <div className="form-group">
                    <label>OTP: </label>
                        <input
                            type="password"
                            value={password}
                            onChange={(e) => setPassword(e.target.value)}
                            placeholder="Enter new password"
                            required
                        />
                        </div>
                        <button type="submit">Change Password</button>
                    </form>
                </div>
            )}
            {message && <p className="booking-success-message">{message}</p>}
            {error && <p className="error-message">{error}</p>}
        </div>
    );
};

export default ForgotPass;
