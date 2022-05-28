#ifndef SCENE_MANAGER_CREATOR_H
#define SCENE_MANAGER_CREATOR_H

#include "scene_manager.h"

class scene_manager_creator
{
public:
    std::shared_ptr<scene_manager> create_man();


private:
    std::shared_ptr<scene_manager> manager_;

    void create_instance();
};

#endif // SCENE_MANAGER_CREATOR_H
