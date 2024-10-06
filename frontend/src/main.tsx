import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import App from './app/App.tsx'
import { BrowserRouter } from 'react-router-dom'
import { ThemeProvider } from './app/providers/Theme/index.ts'
import { StoreProvider } from './app/providers/Store/ ui/StoreProvider.tsx'

window.global = window;

createRoot(document.getElementById('root')!).render(
  <StrictMode>
    <StoreProvider>
      <BrowserRouter>
        <ThemeProvider>
          <App />
        </ThemeProvider>
      </BrowserRouter>
    </StoreProvider>
  </StrictMode>,
)
