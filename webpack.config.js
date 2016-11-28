var webpack = require('webpack');
var ExtractTextPlugin = require('extract-text-webpack-plugin');
module.exports = {
  entry: './src/main/js/app.js',
  output: {
    path: __dirname + '/src/main/resources/static/built',
    filename: 'bundle.js'
  },
  devtool: 'inline-eval-cheap-source-map',
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
        test: /\.scss$/,
        loader: ExtractTextPlugin.extract('css!sass')
      },
      {
        test: /\.(woff2?|ttf|eot|svg|png|jpe?g|gif)$/,
        loader: 'file'
      }
    ]
  },
  plugins: [
    new ExtractTextPlugin('./src/main/resources/static/styles.css', {
      allChunks: true
    })
  ]
};
