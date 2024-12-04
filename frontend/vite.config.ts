import { defineConfig } from 'vite';
import react from '@vitejs/plugin-react';
import svgr from 'vite-plugin-svgr';

interface BuildOptions {
  mode: string;
}

export default ({ mode }: BuildOptions) => {
  const isDev = mode === 'development';
  const port = process.env.VITE_PORT || 3000;
  const apiUrl = isDev ? 'http://localhost:8080' : 'http://localhost:8080';

  return defineConfig({
    plugins: [svgr({ include: '**/*.svg', }), react()],
    resolve: {
      alias: { '@': '/src' },
    },
    define: {
      __IS_DEV__: JSON.stringify(isDev),
      __API__: JSON.stringify(apiUrl),
      __PROJECT__: JSON.stringify('frontend'),
    },
    server: {
      port: Number(port),
    },
  });
};
