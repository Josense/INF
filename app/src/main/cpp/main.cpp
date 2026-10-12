#include <SDL3/SDL.h>
#include <SDL3/SDL_main.h>

int SDL_main(int argc, char *argv[])
{
    SDL_Window *window = nullptr;
    SDL_Renderer *renderer = nullptr;

    SDL_SetHint(SDL_HINT_ORIENTATIONS,
                "LandscapeLeft LandscapeRight Portrait PortraitUpsideDown");

    if (!SDL_CreateWindowAndRenderer(
            "SDL Feature", 1280, 720, SDL_WINDOW_RESIZABLE, &window, &renderer)) {
        SDL_Log("SDL 初始化失败: %s", SDL_GetError());
        return 1;
    }

    bool running = true;
    SDL_Event event;
    constexpr char message[] = "Hello, SDL Android";
    constexpr float textScale = 4.0f;
    constexpr float textWidth =
            (sizeof(message) - 1) * SDL_DEBUG_TEXT_FONT_CHARACTER_SIZE;

    while (running) {
        while (SDL_PollEvent(&event)) {
            if (event.type == SDL_EVENT_QUIT) {
                running = false;
            }
        }

        SDL_SetRenderDrawColor(renderer, 24, 28, 36, 255);
        SDL_RenderClear(renderer);

        int outputWidth = 0;
        int outputHeight = 0;
        if (SDL_GetRenderOutputSize(renderer, &outputWidth, &outputHeight)) {
            SDL_SetRenderScale(renderer, textScale, textScale);
            SDL_SetRenderDrawColor(renderer, 255, 255, 255, 255);
            SDL_RenderDebugText(
                    renderer,
                    (outputWidth / textScale - textWidth) / 2.0f,
                    (outputHeight / textScale - SDL_DEBUG_TEXT_FONT_CHARACTER_SIZE) / 2.0f,
                    message);
            SDL_SetRenderScale(renderer, 1.0f, 1.0f);
        }

        SDL_RenderPresent(renderer);
    }

    SDL_DestroyRenderer(renderer);
    SDL_DestroyWindow(window);
    return 0;
}
