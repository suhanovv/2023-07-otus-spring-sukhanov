const HtmlWebpackPlugin = require('html-webpack-plugin')
const path = require('path');

module.exports = {
    entry: './src/index.tsx',
    devtool: 'inline-source-map',
    mode: 'development',
    output: {
        path: path.resolve(__dirname),
        filename: 'bundle.js',
        libraryTarget: 'umd',
        publicPath: "/"
    },

    devServer: {
        static: path.resolve(__dirname) + '/src',
        compress: true,
        port: 9000,
        host: 'localhost',
        open: true,
        historyApiFallback: true,

        // setupMiddlewares: (middlewares, devServer) => {
        //     middlewares.push({
        //         name: 'authors',
        //         path: '/api/author',
        //         middleware: (req, res) => res.send(
        //             [
        //                 {
        //                     id: 1,
        //                     firstName: "Роберт",
        //                     lastName: "Мартин",
        //                 },
        //                 {
        //                     id: 2,
        //                     firstName: "Герберт",
        //                     lastName: "Шилдт",
        //                 },
        //             ]
        //         )
        //       });
        //
        //     return middlewares;
        // },

        proxy: {
            '/api/*': {
                target: 'http://localhost:8080',
                secure: false,
                changeOrigin: true
            }
        }


    },

    module: {
        rules: [
            {
                test: /\.m?[jt]sx?$/,
                exclude: /(node_modules|bower_components|build)/,
                use: {
                    loader: 'babel-loader',
                    options: {
                        presets: ["@babel/preset-env",
                            ['@babel/preset-react', { runtime: "automatic" }],
                            "@babel/preset-typescript",
                        ]
                    }
                }
            },
            {
                test: /\.css$/i,
                use: ["style-loader", "css-loader"],
            },
        ]
    },
    resolve: {
        extensions: [".js", ".jsx", ".ts", ".tsx"],
    },
    plugins: [
        new HtmlWebpackPlugin({
            filename: 'index.html',
            template: 'public/index.html'
        })
    ]
}