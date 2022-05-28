#ifndef FACADE_H
#define FACADE_H

#include "loader.h"
#include "draw_manager.h"
#include "scene_manager.h"
#include "reform_manager.h"
#include "command.h"

class facade
{
public:
    facade();
    ~facade() = default;

    void execute(base_command &command);

private:
    std::shared_ptr<draw_manager> _drawManager;
    std::shared_ptr<abstract_loader> _loadManager;
    std::shared_ptr<scene_manager> _sceneManager;
    std::shared_ptr<reform_manager> _transformManager;
    std::shared_ptr<scene> _scene;
};

#endif

