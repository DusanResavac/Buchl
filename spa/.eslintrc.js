module.exports = {
  root: true,
  env: {
    node: true,
  },
  extends: [
    'plugin:vue/essential',
    '@vue/airbnb',
  ],
  parserOptions: {
    parser: 'babel-eslint',
  },
  rules: {
    'linebreak-style': ['error', 'windows'],
    'no-param-reassign': 0,
    'no-console': process.env.NODE_ENV === 'production' ? 'warn' : 'off',
    'no-debugger': process.env.NODE_ENV === 'production' ? 'warn' : 'off',
    'consistent-return': 1,
    'spaced-comment': ['error', 'always', {
      line: {
        markers: ['#region', '#endregion', 'region', 'endregion'],
      },
    }],
    'vue/max-len': ['error', {
      'code': 400,
    }],
    'max-len': ['error', {
      'code': 400,
    }],
  },
};
