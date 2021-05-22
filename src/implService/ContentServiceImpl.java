package implService;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import service.ContentService;
import service.DatabaseAkses;
import bean.ContentBean;
import bean.DiscussionBean;
import bean.EventBean;
import bean.FileBean;
import bean.GroupBean;
import bean.ParticipantBean;
import bean.QuestionBean;
import bean.ResultBean;
import bean.UserBean;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.poi.hslf.model.Slide;
import org.apache.poi.hslf.usermodel.SlideShow;

public class ContentServiceImpl implements ContentService {
	private DatabaseAkses database;
	private static ContentServiceImpl instance;
	private HttpServletResponse response;
	private HttpServletRequest request;

	private ContentServiceImpl(DatabaseAkses database,
			HttpServletRequest request, HttpServletResponse response) {
		this.database = database;
		this.request = request;
		this.response = response;
	}

	public static ContentServiceImpl getInstance(DatabaseAkses database,
			HttpServletRequest request, HttpServletResponse response) {
		if (instance == null) {
			instance = new ContentServiceImpl(database, request, response);
		}
		return instance;
	}

	@Override
	public String uploadContent(HttpServletRequest request,
			String finalLocation, String gID, String username) {
		int a = 0;
		boolean isJudulKosong=false;
		String status = "0";
		String nameandExt = "";
		String ext = "";
		String fileName = "";
		String currentName = "";
		String id = "", time = "";
		String title = "", desc = "", cat = "";
		String fullname = "";
		String contentLocation = "";
		FileItem item = null;
		String location = "", category = "";
		int pageSize = 0;
		FileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);
		List items = null;
		try {
			items = upload.parseRequest(request);
		} catch (FileUploadException e) {
			e.printStackTrace();
		}
		Iterator itr = items.iterator();
		while (itr.hasNext()) {
			item = (FileItem) itr.next();
			if (item.isFormField()) {
				String name = item.getFieldName();
				String value = item.getString();
				if (name.equals("title")) {
					title = value;
					if(title.equals("")){isJudulKosong=true;}
				}
				if (name.equals("desc")) {
					desc = value;
				}
				if (name.equals("category2")) {
					cat = value;
					if (cat.equals("")) {
						cat="1";
					}
				}

			}
		}
		itr = items.iterator();
		while (itr.hasNext()) {
			item = (FileItem) itr.next();
			if (item.isFormField()) {

			} else {
				nameandExt = item.getName();
				String splits[] = nameandExt.split("\\.");				
				fileName= nameandExt.substring(0,nameandExt.lastIndexOf('.'));
				if(nameandExt.length()>45)
				fileName = fileName.substring(0,45);
				ext = splits[splits.length-1].toLowerCase();
				System.out.println("until here="+ext);
				// simpan file
				if ((ext.equalsIgnoreCase("png") || ext.equalsIgnoreCase("jpg")
						|| ext.equalsIgnoreCase("jpeg")
						|| ext.equalsIgnoreCase("bmp")
						|| ext.equalsIgnoreCase("gif")
						
						|| ext.equalsIgnoreCase("pdf")
						
						|| ext.equalsIgnoreCase("ppt")
						
						|| ext.equalsIgnoreCase("swf")
						|| ext.equalsIgnoreCase("mp4")) && isJudulKosong==false)  {
					try {
						if (a == 0) {
							try {
								String query = "call UploadContent('"
										+ username + "'," + gID + ",'" + title
										+ "','" + desc + "','4','" + fileName
										+ "','" + ext + "'," + cat + ");";
								System.out.println("query" + query);
								ResultSet rs = database.getInstance()
										.executeSelectQuery(query);
								if (rs.next()) {
									id = rs.getString(1);
									currentName = rs.getString(2);
									time = rs.getString(3);
									fullname = rs.getString(4);
									category = rs.getString(5);

									// Add Notife
									ResultSet rs2 = database
											.getInstance()
											.executeSelectQuery(
													"select insertNotifGroup(' mengunggah berkas di grup ','Controller?action=gDetail&gID=','"
															+ username
															+ "',"
															+ gID + ")");
									if (rs2.next()) {
										if (!rs2.getString(1).equals("null")) {
											int idNotif = rs2.getInt(1);
											String query3 = "call viewNotifGroup('"
													+ username
													+ "',"
													+ gID
													+ ")";
											ResultSet rs3 = database
													.getInstance()
													.executeSelectQuery(query3);
											while (rs3.next()) {
												if (!rs3.getString(1).equals(
														"null")) {
													String user = rs3
															.getString(1);
													String query4 = "select addNotifUser('"
															+ user
															+ "',"
															+ idNotif + ")";
													ResultSet rs4 = database
															.getInstance()
															.executeSelectQuery(
																	query4);
													if (!rs4.next()) {
														id = ("0");
													}
												}
											}

										}
									}
									// End Notife

								}
							} catch (Exception e) {
								status = "0";
								System.out.println(e);
							}
						}
						a++;
						contentLocation = finalLocation
								+ "/sources/GroupContent/" + gID + "/"
								+ currentName;
						finalLocation = finalLocation
								+ "/sources/GroupContent/" + gID + "/"
								+ currentName + "." + ext;
						File savedFile = new File(finalLocation);
						item.write(savedFile);
						location = "sources/GroupContent/" + gID + "/"
								+ currentName + "." + ext;

					} catch (Exception e) {
						System.out.println(e);
						status = "0";
					}
					if (ext.equals("ppt")) {
						System.out.println("ini file ppt");
						convertPPTtoPNG(finalLocation, contentLocation);
						pageSize = pageNumber(finalLocation);
						// System.out.println(currentName+" - "+pageSize);
						String query[] = { "update t_file set PAGE=" + pageSize
								+ " where FILE_ID=" + currentName };
						System.out.println(query);
						database.getInstance().executeUpdateQuery(query);
					}
					String type = "";

					if (ext.equals("mp4") || ext.equals("swf")) {
						type = "<a rel=\"mp4Baru\" href='"
								+ location
								+ "' class=\"video_link\"><button class='glyphicon glyphicon-file btn-delete btn btn-sm' >   Lihat Berkas</button></a>";
					} else if (ext.equals("pdf")) {
						type = "<a id=\"pdfBaru\" href='"
								+ location
								+ "' class=\"video_link\"><button class='glyphicon glyphicon-file btn-delete btn btn-sm' >   Lihat Berkas</button></a>";
					} else if (ext.equals("ppt")) {
						for (int i = 0; i < pageSize; i++) {
							if (i == 0) {
								type = type
										+ "<a rel=\"pptBaru\" href=\"sources/GroupContent/"
										+ gID
										+ "/"
										+ currentName
										+ "/slide-"
										+ (i + 1)
										+ ".png\" title='' id='click'> <button class='glyphicon glyphicon-file btn-delete btn btn-sm' >   Lihat Berkas</button></a>";
							} else {
								type = type
										+ "<a rel=\"pptBaru\" href=\"sources/GroupContent/"
										+ gID
										+ "/"
										+ currentName
										+ "/slide-"
										+ (i + 1)
										+ ".png\" title='' class='viewPPT' style='display: none;'> <button class='glyphicon glyphicon-file btn-delete btn btn-sm' >   Lihat Berkas</button></a>";
							}
						}
					} else {
						type = "<a rel=\"example_group\" href='"
								+ location
								+ "' class=\"video_link\"><button class='glyphicon glyphicon-file btn-delete btn btn-sm' >   Lihat Berkas</button></a>";
					}
					status = "<li class='li' id=\"list"
							+ id
							+ "\"> <div class='direction'> <div class='flag-wrapper'> <span class='flag'><a href=\"Personal?action=vUser&username="
							+ username
							+ "\">"
							+ fullname
							+ "</a> mengupload berkas baru</span> </div> <div class='desc'> <h2> "
							+ title
							+ "<span class='glyphicon glyphicon-remove-circle' id='delIcon' onclick='delBlog("
							+ id
							+ ")'></span><a href=\"Controller?action=editCourse&cID="
							+ id
							+ "\"><span class='glyphicon glyphicon-edit' id='delIcon'></span></a> </h2> <p>"
							+ desc
							+ "</p> "
							+ type
							+ "<p class='kat'>Kategori : <span>"
							+ category
							+ "</span></p></div><div class='lead'> <span class='kanan comment'>0</span> <span class='kanan glyphicon glyphicon-comment'></span> <span class='kanan'>"
							+ time
							+ "</span></div><div class='commentBox'> <div class='form-group'><div class='input-group'><input id=\"body"
							+ id
							+ "\" name='bodyC' type='text' class='form-control' placeholder='Tulis Komentar' maxlength='160' onkeydown='addComment("
							+ id
							+ ")'> <span class='input-group-btn'><button type='submit' class='btn' onclick='aComment("
							+ id
							+ ")'><span class='glyphicon glyphicon-comment'></span></button></span></div></div></div></div></li>";
				} else {
					status = "salah";
					if(isJudulKosong){
						status = "judulKosong";
					}
				}

			}
		}
		System.out.println(status);
		return status;
	}

	public int pageNumber(String source) {
		Slide[] slide = null;
		try {
			FileInputStream is = new FileInputStream(source);
			SlideShow ppt = new SlideShow(is);
			is.close();

			Dimension pgsize = ppt.getPageSize();
			slide = ppt.getSlides();
		} catch (Exception e) {
			System.out.println(e);
		}
		return slide.length;
	}

	public void convertPPTtoPNG(String source, String destination) {
		try {
			new File(destination).mkdir();
			FileInputStream is = new FileInputStream(source);
			SlideShow ppt = new SlideShow(is);
			is.close();

			Dimension pgsize = ppt.getPageSize();

			Slide[] slide = ppt.getSlides();
			for (int i = 0; i < slide.length; i++) {

				BufferedImage img = new BufferedImage(pgsize.width,
						pgsize.height, 1);

				Graphics2D graphics = img.createGraphics();
				graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
						RenderingHints.VALUE_ANTIALIAS_ON);
				graphics.setRenderingHint(RenderingHints.KEY_RENDERING,
						RenderingHints.VALUE_RENDER_QUALITY);
				graphics.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
						RenderingHints.VALUE_INTERPOLATION_BICUBIC);
				graphics.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS,
						RenderingHints.VALUE_FRACTIONALMETRICS_ON);

				graphics.setColor(Color.white);
				graphics.clearRect(0, 0, pgsize.width, pgsize.height);
				graphics.fill(new Rectangle2D.Float(0, 0, pgsize.width,
						pgsize.height));

				// render
				slide[i].draw(graphics);

				// save the output
				FileOutputStream out = new FileOutputStream(destination
						+ "/slide-" + (i + 1) + ".png");
				javax.imageio.ImageIO.write(img, "png", out);
				out.close();
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public void copy(String source, String destination) {
		InputStream inStream = null;
		OutputStream outStream = null;
		try {

			File afile = new File(source);
			File bfile = new File(destination);

			inStream = new FileInputStream(afile);
			outStream = new FileOutputStream(bfile);

			byte[] buffer = new byte[1024];

			int length;
			// copy the file content in bytes
			while ((length = inStream.read(buffer)) > 0) {

				outStream.write(buffer, 0, length);

			}

			inStream.close();
			outStream.close();

			// System.out.println("File is copied successful!");

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<ContentBean> viewContentList(String username) {
		List<ContentBean> contentList = new ArrayList();
		try {
			String query = "CALL viewTimeline ('" + username + "');";
			System.out.println(query);
			ResultSet rs = database.getInstance().executeSelectQuery(query);
			ContentBean content;
			UserBean user;
			GroupBean group;
			while (rs.next()) {
				content = new ContentBean();
				user = new UserBean();
				group = new GroupBean();
				content.setTitle(rs.getString(3));
				content.setBody(rs.getString(4));
				content.setPostedDate(rs.getString(5));
				content.setType(rs.getInt(6));
				content.setId(rs.getInt(7));
				user.setUsername(rs.getString(8));
				content.setCategory(rs.getString(12));
				user.setfName(rs.getString(1));
				user.setlName(rs.getString(2));
				if (rs.getString(9).equals("")) {
					group.setId(0);
					group.setGroupName("");
				} else {
					group.setId(rs.getInt(9));
					group.setGroupName(rs.getString(10));
				}
				content.setGroup(group);
				content.setUser(user);
				if (content.getType() == 4) {
					// content sharing
					String queryFile = "CALL viewFile(" + content.getId()
							+ ");";
					System.out.println(queryFile);
					ResultSet rs2 = database.getInstance().executeSelectQuery(
							queryFile);

					while (rs2.next()) {
						FileBean file = new FileBean();
						file.setId(rs2.getInt(1));
						file.setFileName(rs2.getString(3));
						file.setLocation(rs2.getString(4).toLowerCase());
						file.setPageSize(rs2.getInt(6));
						content.setSingleFile(file);
					}

				} else if (content.getType() == 5) {
					// event
					String queryEvent = "CALL viewEvent(" + content.getId()
							+ ");";
					System.out.println("query : " + queryEvent);
					ResultSet rs3 = database.getInstance().executeSelectQuery(
							queryEvent);
					while (rs3.next()) {
						EventBean event = new EventBean();
						event.setId(rs3.getInt(1));
						event.setStartTime(rs3.getString(2));
						event.setEndTime(rs3.getString(3));
						event.setPlace(rs3.getString(4));
						event.setType(rs3.getInt(5));
						if (event.getType() == 1) {
							List<QuestionBean> questionList = new ArrayList();
							String queryQuestion = "CALL viewQuestion("
									+ event.getId() + ");";
							System.out.println("query : " + queryQuestion);
							ResultSet rs4 = database.getInstance()
									.executeSelectQuery(queryQuestion);
							QuestionBean question;
							while (rs4.next()) {
								// QUESTION,CORRECT,POINT,A,B,C,D,E
								question = new QuestionBean();
								question.setQuestion(rs4.getString(1));
								question.setCorrectAnswer(rs4.getString(2));
								question.setMaxPoint(rs4.getInt(3));
								question.setA(rs4.getString(4));
								question.setB(rs4.getString(5));
								question.setC(rs4.getString(6));
								question.setD(rs4.getString(7));
								question.setE(rs4.getString(8));
								questionList.add(question);
							}
							event.setQuestion(questionList);
						}
						content.setSingleEvent(event);
					}
				}
				// select comment
				String queryComment = "Call viewComment(" + content.getId()
						+ ");";
				ResultSet rs2 = database.getInstance().executeSelectQuery(
						queryComment);
				List<DiscussionBean> commentList = new ArrayList<>();
				DiscussionBean comment;
				while (rs2.next()) {
					comment = new DiscussionBean();
					comment.setId(rs2.getInt(1));
					comment.setTime(rs2.getString(2));
					comment.setComment(rs2.getString(3));
					comment.setCommentator(rs2.getString(4) + " "
							+ rs2.getString(5));
					commentList.add(comment);
				}
				content.setComment(commentList);

				contentList.add(content);

			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return contentList;
	}

	@Override
	public String addCourse(String username, String title, String body,
			String gID, String category) {
		String retursHtml = "false";
		try {
			String query = "Select addBlogGroup('" + username + "', '" + title
					+ "', '" + body + "', " + gID + "," + category + ")";
			ResultSet rs = database.getInstance().executeSelectQuery(query);
			if (rs.next()) {
				retursHtml = (rs.getString(1));
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return retursHtml;
	}

	@Override
	public List<ContentBean> viewCourse(String gID) {
		List<ContentBean> contentList = new ArrayList();
		try {
			String query = "CALL viewCourse (" + gID + ");";
			System.out.println(query);
			ResultSet rs = database.getInstance().executeSelectQuery(query);
			ContentBean content;
			UserBean user;
			while (rs.next()) {
				content = new ContentBean();
				user = new UserBean();
				content.setTitle(rs.getString(3));
				content.setBody(rs.getString(4));
				content.setPostedDate(rs.getString(5));
				content.setType(rs.getInt(6));
				content.setId(rs.getInt(7));
				user.setUsername(rs.getString(8));
				content.setCategory(rs.getString(9));
				user.setfName(rs.getString(1));
				user.setlName(rs.getString(2));
				content.setUser(user);
				// select comment
				String queryComment = "Call viewComment(" + content.getId()
						+ ");";
				ResultSet rs2 = database.getInstance().executeSelectQuery(
						queryComment);
				List<DiscussionBean> commentList = new ArrayList<>();
				DiscussionBean comment;
				while (rs2.next()) {
					comment = new DiscussionBean();
					comment.setId(rs2.getInt(1));
					comment.setTime(rs2.getString(2));
					comment.setComment(rs2.getString(3));
					comment.setCommentator(rs2.getString(4) + " "
							+ rs2.getString(5));
					commentList.add(comment);
				}
				content.setComment(commentList);

				contentList.add(content);

			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return contentList;
	}

	@Override
	public List<ContentBean> viewFileInGroup(String gID) {
		List<ContentBean> contentList = new ArrayList();
		ContentBean content;
		FileBean file;
		try {
			String query = "call viewFileInGroup(" + gID + ")";
			System.out.println(query);
			ResultSet rs = database.getInstance().executeSelectQuery(query);
			while (rs.next()) {
				content = new ContentBean();
				file = new FileBean();
				content.setId(rs.getInt(2));
				// System.out.println(content.getId());
				file.setId(rs.getInt(1));
				file.setFileName(rs.getString(3));
				file.setLocation(rs.getString(4).toLowerCase());
				file.setPageSize(rs.getInt(5));
				content.setSingleFile(file);
				contentList.add(content);
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return contentList;
	}

	@Override
	public String addPostGroup(String username, String gID, String gTtitle,
			String gBody) {
		String htmlReturn = "";
		ContentBean content;
		UserBean user;
		try {
			String query = "select addPosttoGroup('" + username + "'," + gID
					+ ",'" + gTtitle + "','" + gBody + "')";
			System.out.println("query" + query);
			ResultSet rs = database.getInstance().executeSelectQuery(query);
			if (rs.next()) {
				content = new ContentBean();
				content.setId(rs.getInt(1));
				String query2 = "call viewInsertedContent(" + content.getId()
						+ ")";
				System.out.println(query2);
				ResultSet rs2 = database.getInstance()
						.executeSelectQuery(query);
				while (rs2.next()) {
					user = new UserBean();
					content = new ContentBean();
					content.setId(rs.getInt(1));
					user.setUsername(rs.getString(2));

					content.setUser(user);
				}
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return htmlReturn;
	}

	@Override
	public List<ContentBean> viewContent(String cID) {
		List<ContentBean> contentList = new ArrayList();
		try {
			String query = "Call viewDetailContent(" + cID + ");";
			System.out.println(query);
			ResultSet rs = database.getInstance().executeSelectQuery(query);
			ContentBean content;
			UserBean user;
			while (rs.next()) {
				content = new ContentBean();
				user = new UserBean();
				content.setTitle(rs.getString(3));
				content.setBody(rs.getString(4));
				content.setPostedDate(rs.getString(5));
				content.setType(rs.getInt(6));
				content.setId(rs.getInt(7));
				user.setUsername(rs.getString(8));
				content.setCategory(rs.getString(9));
				user.setfName(rs.getString(1));
				user.setlName(rs.getString(2));
				content.setUser(user);
				if (content.getType() == 4) {
					// content sharing
					// content sharing
					String queryFile = "CALL viewFile(" + content.getId()
							+ ");";
					ResultSet rs2 = database.getInstance().executeSelectQuery(
							queryFile);
					while (rs2.next()) {
						FileBean file = new FileBean();
						file.setId(rs2.getInt(1));
						file.setFileName(rs2.getString(3));
						file.setLocation(rs2.getString(4).toLowerCase());
						file.setPageSize(rs2.getInt(6));
						content.setSingleFile(file);
					}
				} else if (content.getType() == 5) {
					// event
					List<EventBean> eventList = new ArrayList();
					String queryEvent = "CALL viewEvent(" + content.getId()
							+ ");";
					System.out.println("query : " + queryEvent);
					ResultSet rs3 = database.getInstance().executeSelectQuery(
							queryEvent);
					EventBean event;
					while (rs3.next()) {
						event = new EventBean();
						event.setId(rs3.getInt(1));
						event.setStartTime(rs3.getString(2));
						event.setEndTime(rs3.getString(3));
						event.setPlace(rs3.getString(4));
						event.setType(rs3.getInt(5));
						if (event.getType() == 1) {
							List<QuestionBean> questionList = new ArrayList();
							String queryQuestion = "CALL viewQuestion("
									+ event.getId() + ");";
							System.out.println("query : " + queryQuestion);
							ResultSet rs4 = database.getInstance()
									.executeSelectQuery(queryQuestion);
							QuestionBean question;
							while (rs4.next()) {
								// QUESTION,CORRECT,POINT,A,B,C,D,E
								question = new QuestionBean();
								question.setQuestion(rs4.getString(1));
								question.setCorrectAnswer(rs4.getString(2));
								question.setMaxPoint(rs4.getInt(3));
								question.setA(rs4.getString(4));
								question.setB(rs4.getString(5));
								question.setC(rs4.getString(6));
								question.setD(rs4.getString(7));
								question.setE(rs4.getString(8));
								questionList.add(question);
							}
							event.setQuestion(questionList);
						}
						eventList.add(event);
					}
					content.setEvent(eventList);
				}
				// select comment
				String queryComment = "Call viewComment(" + content.getId()
						+ ");";
				ResultSet rs2 = database.getInstance().executeSelectQuery(
						queryComment);
				List<DiscussionBean> commentList = new ArrayList<>();
				DiscussionBean comment;
				while (rs2.next()) {
					comment = new DiscussionBean();
					comment.setId(rs2.getInt(1));
					comment.setTime(rs2.getString(2));
					comment.setComment(rs2.getString(3));
					comment.setCommentator(rs2.getString(4) + " "
							+ rs2.getString(5));
					commentList.add(comment);
				}
				content.setComment(commentList);

				contentList.add(content);

			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return contentList;
	}

	@Override
	public List<ContentBean> viewQuestionList(String gID, String username) {
		List<ContentBean> contentList = new ArrayList();
		EventBean event;
		// QuestionBean quiz;
		// List<QuestionBean> quizList=new ArrayList();
		try {
			String query = "call showQuizList(" + gID + ",'" + username + "')";
			System.out.println("query : " + query);
			ResultSet rs = database.getInstance().executeSelectQuery(query);
			while (rs.next()) {
				event = new EventBean();
				event.setId(rs.getInt(1));
				event.setTitle(rs.getString(2));
				event.setBody(rs.getString(3));
				event.setStartTime(rs.getString(4));
				event.setEndTime(rs.getString(5));
				event.setTime(rs.getInt(6));
				contentList.add(event);
			}
		} catch (Exception e) {

		}
		return contentList;
	}

	@Override
	public EventBean attemptDoQuiz(String username, String groupID,
			String eventID) {
		QuestionBean question;
		String memberID = "";
		List<QuestionBean> questionList = new ArrayList();
		ParticipantBean participant = new ParticipantBean();
		EventBean event = new EventBean();
		event.setId(Integer.parseInt(eventID));
		try {
			String queryMID = "select member_id from t_member where username='"
					+ username + "' and GROUP_ID=" + groupID;
			ResultSet rsMID = database.getInstance().executeSelectQuery(
					queryMID);
			if (rsMID.next())
				memberID = rsMID.getString(1);

			String query = "select attempQuiz(" + memberID + "," + eventID
					+ ")";
			// select attempQuiz('FHR', 25, 12);
			System.out.println("query : " + query);
			ResultSet rs = database.getInstance().executeSelectQuery(query);
			int a = 0;
			if (rs.next()) {
				String feedBackID = rs.getString(1);
				participant.setParticipantID(rs.getInt(1));
				String query2 = "select question_id from t_question where event_id="
						+ eventID;
				System.out.println("query 2 : " + query2);
				ResultSet rs2 = database.getInstance().executeSelectQuery(
						query2);
				while (rs2.next()) {
					question = new QuestionBean();
					question.setId(rs2.getInt(1));
					String query3[] = { "insert into t_result (feedback_id, question_id, answer, point) values ("
							+ feedBackID + ", " + question.getId() + ", '', 0)" };
					System.out.println(query3[0]);
					boolean status = database.getInstance().executeUpdateQuery(
							query3);
					if (!status)
						a++;
				}
				System.out.println("apakah berhasil : " + a);
			}
			if (a == 0) {
				String query4 = "select * from t_question where event_id="
						+ eventID;
				ResultSet rs4 = database.getInstance().executeSelectQuery(
						query4);
				while (rs4.next()) {
					question = new QuestionBean();
					question.setId(rs4.getInt(1));
					question.setQuestion(rs4.getString(3));
					question.setCorrectAnswer(rs4.getString(4));
					question.setMaxPoint(rs4.getInt(5));
					question.setA(rs4.getString(6));
					question.setB(rs4.getString(7));
					question.setC(rs4.getString(8));
					question.setD(rs4.getString(9));
					question.setE(rs4.getString(10));
					questionList.add(question);
				}
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		event.setQuestion(questionList);
		event.setParticipant(participant);
		return event;
	}

	public int getTime(int fID) {
		int time = 0;
		try {
			String query = "select timeleft from t_feedback where feedback_id="
					+ fID;
			System.out.println(query);
			ResultSet rs = database.getInstance().executeSelectQuery(query);
			if (rs.next()) {
				time = rs.getInt(1);
			} else {
			}
			// String
			// query2[]={"update t_feedback set timeleft="+(time-1)+"where feedback_id="+feedBackID};
			// database.getInstance().executeUpdateQuery(query2);
		} catch (Exception e) {
		}
		return time;
	}

	public String updateAnswer(String Answer, int fID, String qID) {
		String htmlReturn = "Update jawaban gagal";
		String query[] = { "update t_result set answer='" + Answer
				+ "' where feedback_id=" + fID + " and question_id=" + qID };
		boolean status = database.getInstance().executeUpdateQuery(query);
		if (status) {
			htmlReturn = "Update jawaban berhasil";
		}
		return htmlReturn;
	}

	@Override
	public List<ContentBean> showScore(String gID, String username) {
		List<ContentBean> contentList = new ArrayList();
		ContentBean content;
		EventBean event;
		ParticipantBean participant;
		ResultBean result;
		try {
			String query = "call showScore(" + gID + ",'" + username + "')";
			System.out.println(query);
			ResultSet rs = database.getInstance().executeSelectQuery(query);
			while (rs.next()) {
				result = new ResultBean();
				content = new ContentBean();
				participant = new ParticipantBean();
				event = new EventBean();
				content.setTitle(rs.getString(1));
				participant.setParticipantID(rs.getInt(2));
				event.setId(rs.getInt(3));
				result.setScore(rs.getFloat(5));
				// -----//
				participant.setResult(result);
				event.setParticipant(participant);
				content.setSingleEvent(event);
				contentList.add(content);
			}
		} catch (Exception e) {

		}
		return contentList;
	}

	public String showScoreDetail(String eID, String fID) {
		int a = 1;
		String htmlReturn = "";
		ResultBean result;
		QuestionBean question;
		try {
			String query = "call showScoreDetail(" + eID + "," + fID + ")";
			System.out.println(query);
			ResultSet rs = database.getInstance().executeSelectQuery(query);
			while (rs.next()) {
				question = new QuestionBean();
				result = new ResultBean();
				question.setQuestion(rs.getString(2));
				question.setA(rs.getString(5));
				question.setB(rs.getString(6));
				question.setC(rs.getString(7));
				question.setD(rs.getString(8));
				question.setE(rs.getString(9));
				question.setCorrectAnswer(rs.getString(3));
				result.setAnswer(rs.getString(4));
				if (question.getCorrectAnswer().equals(result.getAnswer())) {
					htmlReturn += a + ".<b> " + question.getQuestion()
							+ "</b><br>A. " + question.getA() + "<br>B. "
							+ question.getB() + "<br>C. " + question.getC()
							+ "<br>D. " + question.getD() + "<br>E. "
							+ question.getE() + "<br>";
				} else {
					htmlReturn += a + ".<b> " + question.getQuestion()
							+ "</b><font color=red><br>Jawaban Yang Benar : "
							+ question.getCorrectAnswer()
							+ "</font><br>Jawaban Anda : " + result.getAnswer()
							+ "<br>A. " + question.getA() + "<br>B. "
							+ question.getB() + "<br>C. " + question.getC()
							+ "<br>D. " + question.getD() + "<br>E. "
							+ question.getE() + "<br>";
				}
				a++;
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return htmlReturn;
	}

}
