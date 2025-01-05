import axios from 'axios';
import React from 'react';


const getAxios = () => {
    console.log("TOKEN=",localStorage.getItem('authtoken'));
    const headers = {
        "Content-Type": "application/json",
        "Accept": "application/json",
        'Authorization':`Bearer  ${localStorage.getItem('authtoken')}`,
    };
    const instance = axios.create({
        headers
    });

    return instance;

}
export default getAxios();