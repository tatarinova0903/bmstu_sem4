#ifndef SCENE_COMMAND_H
#define SCENE_COMMAND_H

#include <memory>

#include "drawer.h"
#include "command.h"

class  base_scene_command : public base_command {};

class draw_scene : public base_scene_command {
public:
    explicit draw_scene(std::shared_ptr<base_drawer> d);

    draw_scene() = delete;

    ~draw_scene() override = default;

    void execute() override;

private:
    std::shared_ptr<base_drawer> drawer_;
};


class load_scene : public base_scene_command {
public:
    explicit load_scene(std::string fn);

    load_scene() = delete;

    ~load_scene() override = default;

    void execute() override;

private:
    std::string filename_;
};


#endif // SCENE_COMMAND_H
