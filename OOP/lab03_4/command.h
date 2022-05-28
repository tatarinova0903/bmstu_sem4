#ifndef COMMAND_H
#define COMMAND_H

#include <string>
#include "draw_manager.h"
#include "loader.h"
#include "scene_manager.h"
#include "reform_manager.h"
#include "scene.h"

class base_command
{
public:
    base_command() = default;
    virtual ~base_command() = default;
    virtual void execute() = 0;

    virtual void setManagers(std::shared_ptr<draw_manager> draw_manager,
                             std::shared_ptr<abstract_loader> load_manager,
                             std::shared_ptr<scene_manager> scene_manager,
                             std::shared_ptr<reform_manager> reform_manager);

protected:
    std::shared_ptr<draw_manager> _draw_manager;
    std::shared_ptr<abstract_loader> _load_manager;
    std::shared_ptr<scene_manager> _scene_manager;
    std::shared_ptr<reform_manager> _reform_manager;
};

#endif
