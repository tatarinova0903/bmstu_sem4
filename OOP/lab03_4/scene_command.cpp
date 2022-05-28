#include <utility>

#include "scene_command.h"

draw_scene::draw_scene(std::shared_ptr<base_drawer> d)
    : drawer_(std::move(d))
{}

void draw_scene::execute()
{
    drawer_->clear_scene();
}



load_scene::load_scene(std::string fn)
    : filename_(std::move(fn)) {}

void load_scene::execute()
{
    auto s = std::dynamic_pointer_cast<scene>(_load_manager->load_model(filename_));
    _scene_manager->set_scene(s);
}
