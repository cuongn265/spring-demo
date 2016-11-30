var webpack = require('webpack');
module.exports = {
  entry: './src/main/js/app.js',
  output: {
    path: __dirname + '/src/main/resources/static/built',
    filename: 'bundle.js'
  },
  devtool: 'eval',
  module: {
    loaders: [
      {
        test: /.js$/,
        loader: 'babel',
        exclude: /node_modules/,
        query: {
          presets: ['es2015', 'react']
        }
      },
      {
        test: /\.css$/,
        loader: 'style!css'
      },
      {
        test: /\.(woff2?|ttf|eot|svg|png|jpe?g|gif)$/,
        loader: 'file'
      }
    ]
  }
};
