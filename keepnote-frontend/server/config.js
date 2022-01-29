module.exports = {
  users: {
    admin: {password: 'password', scopes: 'notes:all notes:read notes:edit reminders:all reminders:read reminders:edit'.split(' ')},
    stranger: {password: 'password', scopes: 'notes:all notes:read'.split(' ')}
  },
  jwtSecret: '08098grrgaorugh',
  port: 3000,
  enableAuth: true
}
