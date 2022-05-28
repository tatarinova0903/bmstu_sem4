#include <utility>

#include "scene_command.h"
#include "draw_manager_creator.h"
#include "scene_manager_creator.h"
#include "load_manager_cretor.h"

draw_scene::draw_scene(std::shared_ptr<base_drawer> d)
    : drawer_(std::move(d))
{}

void draw_scene::execute()
{
    auto dm = draw_manager_creator().create_man();
    auto sm = scene_manager_creator().create_man();

    drawer_->clear_scene();
    dm->set_drawer(drawer_);
    dm->set_cam(sm->get_cam());
    sm->get_scene()->get_composite()->accept(dm);

}



load_scene::load_scene(std::string fn)
    : filename_(std::move(fn)) {}

void load_scene::execute()
{
    auto m = load_manager_cretor().create_man();
//    std::shared_ptr<LoaderBaseScene> p(new LoaderFileScene);
//    m->set_loader(std::shared_ptr<AbstractLoadController> (new LoadSceneController(p)));

//    auto s = std::dynamic_pointer_cast<Scene>(m->load(filename_));

//    SceneManagerCreator().create_man()->set_scene(s);
}
