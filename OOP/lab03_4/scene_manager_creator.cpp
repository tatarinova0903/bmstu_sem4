#include "scene_manager_creator.h"

void scene_manager_creator::create_instance() {
    static std::shared_ptr<scene_manager> manager(new scene_manager());

    manager_ = manager;
}


std::shared_ptr<scene_manager> scene_manager_creator::create_man()
{
    if (manager_ == nullptr)
    {
        create_instance();
    }

    return manager_;
}

