import axios from 'axios';

export default {

  login(user) {
    return axios.post('/login', user)
  },

  register(user) {
    return axios.post('/register', user)
  },

  getUserProfile(userId) {
    return axios.get(`/users/${userId}`);
  },

  updateUserProfile(user) {
    return axios.put('/users', user)
  }

}
