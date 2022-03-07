module.exports = [
  {
    context: ['/api/**', '/secure/api/**'],
    target: 'http://localhost:8080',
    secure: false,
    logLevel: 'debug'
  }
]
