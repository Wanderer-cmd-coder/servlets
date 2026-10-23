package ru.netology.repository;

import ru.netology.exception.NotFoundException;
import ru.netology.model.Post;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

// Stub
public class PostRepository {

  private final Map<Long, Post> posts = new ConcurrentHashMap<>();
  private final AtomicLong idCounter = new AtomicLong(0);

  public List<Post> all() {
    return new ArrayList<Post>(posts.values());
  }

  public Optional<Post> getById(long id) {
    return Optional.ofNullable(posts.get(id));
  }

  public Post save(Post post) {
    if (post.getId() == 0) {
      post.setId(idCounter.incrementAndGet());
      posts.put(post.getId(), post);
      return post;
    }

    if (posts.replace(post.getId(), post) == null) {
      throw new NotFoundException();
    }
    return post;
  }

  public void removeById(long id) {
    posts.remove(id);
  }
}
