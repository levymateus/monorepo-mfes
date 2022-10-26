const { merge } = require("webpack-merge");
const singleSpaDefaults = require("webpack-config-single-spa-react-ts");

module.exports = (webpackConfigEnv, argv) => {
  const defaultConfig = singleSpaDefaults({
    orgName: "apps",
    projectName: "root",
    webpackConfigEnv,
    argv,
  });

  return merge(defaultConfig, {
    stats: "errors-only",
    module: {
      rules: [
        {
          test: /\.svg$/,
          loader: "svg-inline-loader",
        },
      ],
    },
  });
};
