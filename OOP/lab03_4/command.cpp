#include "command.h"

void base_command::setManagers(std::shared_ptr<draw_manager> draw_manager,
                              std::shared_ptr<abstract_loader> load_manager,
                              std::shared_ptr<scene_manager> scene_manager,
                              std::shared_ptr<reform_manager> reform_manager)
{
    _draw_manager = draw_manager;
    _load_manager = load_manager;
    _scene_manager = scene_manager;
    _reform_manager = reform_manager;
}
