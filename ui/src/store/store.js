import { createStore } from 'vuex'

const store = createStore({
  state() {
    return {
      user: {
        username: JSON.parse(localStorage.getItem('user'))?.username || '',
        token: JSON.parse(localStorage.getItem('user'))?.token || '',
        email: JSON.parse(localStorage.getItem('user'))?.email || '',
        img: JSON.parse(localStorage.getItem('user'))?.img || 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTpmcY7L1pv0ViLQUKnDEA0eQ8NP-qgLrSjZg&usqp=CAU',
      },
      logIn: false
    }
  },
  getters: {
    getUser(state) {
      return state.user
    },
    getLogIn(state) {
      if (state.user.token)
        return true
      return false
    }
  },
  mutations: {
    init(state,user) {
      state.user = user
      state.logIn = true
      localStorage.setItem('user', JSON.stringify(user))
    }, 
    logOut(state) {
      state.user = {}
      state.logIn = false
      localStorage.removeItem('user')
    }
  }
})

export default store