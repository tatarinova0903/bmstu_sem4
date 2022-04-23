#ifndef LIST_H
#define LIST_H

#include <time.h>
#include <typeinfo>
#include <memory>
#include "base_container.h"
#include "list_iterator.h"
#include "const_list_iterator.h"
#include "node.h"
#include "exceptions.h"

using namespace std;

template <typename T>
class List : public container::base_container
{
public:
    List(): head(nullptr), tail(nullptr){}
    List(T* array, int n);
    explicit List(const List<T> &list);
    List(List<T> &&list) noexcept;
    List(initializer_list<T> lst);

    virtual ~List() override;
    List<T>& operator =(const List<T> &list);
    List<T>& operator =(List<T> &&list);
    List<T>& operator =(initializer_list<T> lst);

    List<T> unite(const List<T> &list) const;
    List<T> operator +(const List<T> &list) const;

    List<T> unite(const T &value) const;
    List<T> operator +(const T &value) const;

    List<T>& append(const List<T>& list);
    List<T>& operator +=(const List<T> &list);

    List<T>& append(const T& elem);
    List<T>& operator +=(const T &value);

    List<T>& push_back(const T& value);
    List<T>& push_front(const T& value);

    List<T>& insert_after(const T& el, list_iterator<T>& insert_after);
    List<T>& insert_after(const List<T> &list, list_iterator<T>& insert_after);

    bool compare(const List<T>& list) const;
    bool operator ==(const List<T> &list) const;
    bool operator !=(const List<T> &list) const;

    list_iterator<T> begin();
    list_iterator<T> end();
    const_list_iterator<T> cbegin() const;
    const_list_iterator<T> cend() const;

    void clear();

    List<T>& remove(list_iterator<T>& iter);
    T& pop_front();

    bool is_empty();

    T& get_first();
    const T& get_first() const;
    T& get_last();
    const T& get_last() const;

protected:
    List<T>& append_list(const List<T>& list);
    List<T>& append_el(const T& elem);
    List<T>& append(shared_ptr<Node<T>> el);
    List<T>& insert_after(shared_ptr<Node<T>> el, list_iterator<T>& insert_after);
    void free();

private:
    shared_ptr<Node<T>> head;
    shared_ptr<Node<T>> tail;
};

#include "list_implement.h"
#endif // LIST_H
